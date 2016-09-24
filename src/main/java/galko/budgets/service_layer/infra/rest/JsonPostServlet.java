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

public abstract class JsonPostServlet<TRequest, TResponse> extends HttpServlet {

    private final static Logger logger = LogManager.getLogger(JsonPostServlet.class);

    protected abstract Class<TRequest> getRequestType();

    protected abstract TResponse doJson(TRequest request);

    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html");

        Gson gson = new Gson();
        TRequest requestObj = gson.fromJson(request.getReader(), getRequestType());
        TResponse responseObj = doJson(requestObj);
        PrintWriter out = response.getWriter();
        gson.toJson(responseObj, out);
    }

    public void destroy()
    {
        // do nothing.
    }
}
