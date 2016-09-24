package galko.budgets.service_layer.infra.rest;

import com.google.gson.Gson;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class JsonGetServlet<TResponse> extends HttpServlet {

    private final static Logger logger = LogManager.getLogger(JsonGetServlet.class);

    protected abstract TResponse doJson(GetRequest request);

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html");

        Gson gson = new Gson();
        TResponse responseObj = doJson(newGetRequest(request));
        PrintWriter out = response.getWriter();
        gson.toJson(responseObj, out);
    }

    private GetRequest newGetRequest(HttpServletRequest servletRequest) {
        return GetRequest.setup()
                .withPathInfo(parsePathInfo(servletRequest))
                .create();
    }

    private String[] parsePathInfo(HttpServletRequest servletRequest) {
        if (servletRequest.getPathInfo() == null) {
            return new String[0];
        }
        String str = trim(servletRequest.getPathInfo(), '/');
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

    public void destroy()
    {
        // do nothing.
    }
}
