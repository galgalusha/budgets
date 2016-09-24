package galko.budgets.service_layer.infra.rest;

import java.util.Arrays;
import java.util.List;

public class GetRequest {

    private final String[] pathInfo;

    private GetRequest(String[] pathInfo) {
        this.pathInfo = pathInfo;
    }

    public List<String> getPathInfo() {
        return Arrays.asList(pathInfo);
    }

    public static Builder setup() {
        return new Builder();
    }

    public static class Builder {

        private String[] pathInfo = new String[0];

        public Builder withPathInfo(String[] value) {
            pathInfo = value;
            return this;
        }

        public GetRequest create() {
            return new GetRequest(pathInfo);
        }
    }
}
