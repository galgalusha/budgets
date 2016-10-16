package galko.budgets.web.infra.rest;

import com.google.gson.Gson;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class ServiceDispatchServlet extends HttpServlet {

    private final static Logger logger = LogManager.getLogger(ServiceDispatchServlet.class);

    private RouteRegistry routeRegistry = new RouteRegistry();

    public void setRouteRegistry(RouteRegistry value) {
        routeRegistry = value;
    }

    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        handleRequest(request, response, HttpMethod.Post);
    }

    @Override
    public void doGet(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException
    {
        handleRequest(request, response, HttpMethod.Get);
    }

    private void handleRequest(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpMethod method) throws ServletException, IOException {

        response.setContentType("text/html");
        Optional<Route> route = resolveRoute(request, method);
        if (!route.isPresent()) {
            throw new ServletException("could not find service for route url: " + request.getRequestURI());
        }
        Object serviceArg = route.get().requestToJsonMapper.apply(request);
        Object serviceResult = route.get().service.handle(serviceArg);
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        gson.toJson(serviceResult, out);
    }

    public Optional<Route> resolveRoute(HttpServletRequest request, HttpMethod method) {
        return routeRegistry.getRoutes()
                .filter(route -> route.method == method)
                .filter(route -> request.getPathInfo().startsWith(route.pathPrefix))
                .findFirst();
    }

    public void destroy()
    {
        // do nothing.
    }
}
