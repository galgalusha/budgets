package galko.budgets.web.infra.rest;

import galko.budgets.business.api.web.dto.*;
import galko.budgets.business.services.*;
import org.jooq.lambda.Seq;
import java.util.Comparator;
import java.util.List;
import static galko.budgets.web.infra.rest.PathInfoToRequest.toInteger;
import static org.jooq.lambda.Seq.seq;

public class RouteRegistry {

    private final Comparator<Route> byPathLengthDesc =
            (r1, r2) -> r2.pathPrefix.length() - r1.pathPrefix.length();

    private final List<Route> routes = Seq.of(

            new Route(
                    "/budgets/rest/budgets",
                    HttpMethod.Get,
                    new GetAllBudgetsService()),

            new Route(
                    "/budgets/rest/budget",
                    HttpMethod.Get,
                    new PathInfoToRequest(GetBudgetRequest.class)
                            .parseArg(Index.of(1), toInteger())
                            .andThen(addUserId()),
                    new GetBudgetService()),

            new Route(
                    "/budgets/rest/newExpense",
                    HttpMethod.Post,
                    new GsonToRequest<>(NewExpenseRequest.class).andThen(addUserId()),
                    new NewExpenseService())

    ).sorted(byPathLengthDesc)
     .toList();

    public Seq<Route> getRoutes() {
        return seq(routes);
    }

    private AddUserIdFromJwt addUserId() {
        return new AddUserIdFromJwt();
    }
}
