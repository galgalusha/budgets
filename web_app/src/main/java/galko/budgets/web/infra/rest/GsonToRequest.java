package galko.budgets.web.infra.rest;

import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;

public class GsonToRequest<TREQUEST> implements Function<HttpServletRequest, TREQUEST> {

    private final Class<TREQUEST> requestType;

    public GsonToRequest(Class<TREQUEST> requestType) {
        this.requestType = requestType;
    }

    @Override
    public TREQUEST apply(HttpServletRequest request) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(request.getReader(), requestType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
