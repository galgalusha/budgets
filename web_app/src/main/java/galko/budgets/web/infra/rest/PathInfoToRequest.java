package galko.budgets.web.infra.rest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple2;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static org.jooq.lambda.Seq.seq;

public class PathInfoToRequest<TREQUEST> implements Function<HttpServletRequest, TREQUEST> {

    private final Class<TREQUEST> requestClass;
    private final List<ArgMapper> argMappers = new LinkedList<>();
    private final PathInfoParser pathInfoParser = new PathInfoParser();
    private final static Logger logger = LogManager.getLogger(PathInfoToRequest.class);

    public PathInfoToRequest(Class<TREQUEST> requestClass) {
        this.requestClass = requestClass;
    }

    @Override
    public TREQUEST apply(HttpServletRequest servletRequest) {

        Constructor constructor = Arrays.stream(requestClass.getConstructors())
                .filter(c -> c.getParameterCount() == argMappers.size())
                .findFirst()
                .get();

        List<Tuple2<Tuple2<String, Long>, ArgMapper>> valuesAndMappers =
                Seq.of(pathInfoParser.parsePathInfo(servletRequest))
                        .zipWithIndex()
                        .innerJoin(argMappers, (pathArg, mapper) -> pathArg.v2.intValue() == mapper.index)
                        .toList();

        if (valuesAndMappers.size() != argMappers.size()) {
            final String msg =
                    "Could not map some of the path-info args to the constructor params. \r\n" +
                    "class: " + requestClass.getName() + "\r\n" +
                    "constructor arg counts: " + constructor.getParameterCount() + "\r\n" +
                    "path-info: " + servletRequest.getPathInfo() + "\r\n" +
                    "arg-mappers: " + seq(argMappers).map(ArgMapper::toString).toString(",");
            logger.error(msg);
            throw new RuntimeException(msg);
        }

        Object[] args = seq(valuesAndMappers)
                .map(x -> x.v2().mappingFunc.apply(x.v1().v1()))
                .toArray();

        try {
            return (TREQUEST) constructor.newInstance(args);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PathInfoToRequest parseArg(Index index, Function<String, Object> mappingFunc) {
        argMappers.add(new ArgMapper(index, mappingFunc));
        return this;
    }

    public static class ArgMapper {
        public ArgMapper(Index index, Function<String, Object> mappingFunc) {
            this.index = index.value;
            this.mappingFunc = mappingFunc;
        }

        public final int index;
        public final Function<String, Object> mappingFunc;

        @Override
        public String toString() {
            return "{ArgMapper of Index: " + index + "}";
        }


    }

    public static Function<String, Object> toInteger() {
        return Integer::parseInt;
    }
}
