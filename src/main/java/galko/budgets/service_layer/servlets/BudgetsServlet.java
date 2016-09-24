package galko.budgets.service_layer.servlets;

import galko.budgets.service_layer.dto.Budget;
import galko.budgets.service_layer.infra.rest.GetRequest;
import galko.budgets.service_layer.infra.rest.JsonGetServlet;

import java.util.Arrays;
import java.util.List;

public class BudgetsServlet extends JsonGetServlet<List<Budget>> {

    @Override
    protected List<Budget> doJson(GetRequest request) {
        return Arrays.asList(
                Budget.config()
                        .withName("Groceries")
                        .withId(1l)
                        .withAllocation(2000)
                        .withUsed(750)
                        .create()
                ,
                Budget.config()
                        .withName("Dressing")
                        .withId(2l)
                        .withAllocation(500)
                        .withUsed(0)
                        .create()
                ,
                Budget.config()
                        .withName("Misc")
                        .withId(3l)
                        .withAllocation(1500)
                        .withUsed(2000)
                        .create()
        );
    }
}
