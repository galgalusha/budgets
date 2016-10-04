package galko.budgets.web.infra.rest;

import galko.budgets.business.api.web.dto.Request;
import galko.budgets.web.infra.jwt.JwtParser;
import org.jooq.lambda.function.Consumer2;
import javax.servlet.http.HttpServletRequest;

public class AddUserIdFromJwt<T extends Request> implements Consumer2<HttpServletRequest, T> {

    @Override
    public void accept(HttpServletRequest servletRequest, T businessRequest) {
        final String userId = JwtParser.parse(servletRequest.getHeader("authorization")).getSub();
        businessRequest.setUserId(userId);
    }

    public static AddUserIdFromJwt addUserId() {
        return new AddUserIdFromJwt();
    }
}
