package galko.budgets.web.infra.rest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;

public class FirstArgToInteger implements Function<HttpServletRequest, Object> {

    private final static Logger logger = LogManager.getLogger(FirstArgToInteger.class);

    private final PathInfoParser pathInfoParser;

    public FirstArgToInteger(StartingAfterPrefix startingAfterPrefix) {
        this.pathInfoParser = new PathInfoParser(startingAfterPrefix);
    }

    @Override
    public Object apply(HttpServletRequest request) {
        String firstArg = pathInfoParser.parsePathInfo(request)[0];
        return Integer.parseInt(firstArg);
    }
}
