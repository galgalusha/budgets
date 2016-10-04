package galko.budgets.web.infra.rest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class PathInfoParser {

    private final static Logger logger = LogManager.getLogger(PathInfoParser.class);

    private final Optional<StartingAfterPrefix> prefixToRemove;

    public PathInfoParser(StartingAfterPrefix prefixToRemove) {
        this.prefixToRemove = Optional.of(prefixToRemove);
    }

    public PathInfoParser() {
        this.prefixToRemove = Optional.empty();
    }

    public String[] parsePathInfo(HttpServletRequest request) {
        return parsePathInfo(getPathInfoWithoutPrefix(request));
    }

    private String getPathInfoWithoutPrefix(HttpServletRequest request) {
        if (request.getPathInfo() == null) {
            String msg = "request has no pathInfo. request: " + request.getRequestURI();
            logger.error(msg);
            throw new RuntimeException(msg);
        }

        if (!prefixToRemove.isPresent()) {
            return request.getPathInfo();
        }

        final String pathInfo = trim(request.getPathInfo(), '/');

        if (!pathInfo.startsWith(prefixToRemove.get().value)) {
            String msg = "request pathInfo does not start with prefix \"" + prefixToRemove.get().value + "\". request: " + request.getRequestURI() + ". pathInfo: " + pathInfo;
            logger.error(msg);
            throw new RuntimeException(msg);
        }

        return pathInfo.substring(prefixToRemove.get().value.length());
    }


    private String[] parsePathInfo(String pathInfo) {
        if (pathInfo == null) {
            return new String[0];
        }
        String str = trim(pathInfo, '/');
        if (str.isEmpty()) {
            return new String[0];
        }
        return str.split("/");
    }

    private String trim(String s, char c) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        if (s.charAt(0) == c) {
            return trim(s.substring(1), c);
        }
        if (s.charAt(s.length() - 1) == c) {
            return trim(s.substring(0, s.length() - 2), c);
        }
        return s;
    }
}
