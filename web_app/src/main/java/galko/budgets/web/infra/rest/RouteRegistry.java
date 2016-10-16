package galko.budgets.web.infra.rest;

import galko.budgets.business.api.web.dto.*;
import galko.budgets.business.services.*;
import org.jooq.lambda.Seq;
import java.util.Comparator;
import java.util.List;
import static galko.budgets.web.infra.rest.AddUserIdFromJwt.addUserId;
import static galko.budgets.web.infra.rest.PathInfoToRequest.toInteger;
import static galko.budgets.web.infra.rest.Route.createEmptyRequest;
import static org.jooq.lambda.Seq.seq;

public class RouteRegistry {

    private final Comparator<Route> byPathLengthDesc =
            (r1, r2) -> r2.pathPrefix.length() - r1.pathPrefix.length();

    private final List<Route> routes = Seq.of(

            new Route(
                    "/activeBills",
                    HttpMethod.Get,
                    createEmptyRequest().andThen(addUserId()),
                    new GetActiveBills()),

            new Route(
                    "/budget",
                    HttpMethod.Get,
                    new PathInfoToRequest(GetBudgetRequest.class)
                            .parseArg(Index.of(1), toInteger())
                            .andThen(addUserId()),
                    new GetBudgetService()),

            new Route(
                    "/newExpense",
                    HttpMethod.Post,
                    new GsonToRequest<>(NewExpenseRequest.class).andThen(addUserId()),
                    new NewExpenseService())

    ).sorted(byPathLengthDesc)
    .toList();

    public Seq<Route> getRoutes() {
        return seq(routes);
    }
}
