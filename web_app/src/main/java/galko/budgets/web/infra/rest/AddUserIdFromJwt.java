package galko.budgets.web.infra.rest;

import galko.budgets.business.api.web.dto.Request;
import java.util.function.Function;

public class AddUserIdFromJwt<T extends Request> implements Function<T, T> {

    @Override
    public T apply(T request) {
        return request;
    }
}
