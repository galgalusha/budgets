package galko.budgets.web.infra.rest;

import galko.budgets.business.api.web.IService;
import galko.budgets.business.api.web.dto.EmptyRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;

public class Route {

    public final String pathPrefix;
    public final HttpMethod method;
    public final Function<HttpServletRequest, ? extends Object> requestToJsonMapper;
    public final IService service;

    public Route(String pathPrefix, HttpMethod method, Function<HttpServletRequest, ? extends Object> requestToJsonMapper, IService service) {
        this.pathPrefix = pathPrefix;
        this.method = method;
        this.requestToJsonMapper = requestToJsonMapper;
        this.service = service;
    }

    public Route(String pathPrefix, HttpMethod method, IService service) {
        this.pathPrefix = pathPrefix;
        this.method = method;
        this.requestToJsonMapper = x -> new EmptyRequest();
        this.service = service;
    }

}
