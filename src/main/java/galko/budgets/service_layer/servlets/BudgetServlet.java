package galko.budgets.service_layer.servlets;

import galko.budgets.service_layer.dto.Budget;
import galko.budgets.service_layer.infra.rest.GetRequest;
import galko.budgets.service_layer.infra.rest.JsonGetServlet;

public class BudgetServlet extends JsonGetServlet<Budget> {

    @Override
    protected Budget doJson(GetRequest request) {
        return Budget.config()
                        .withName("Groceries")
                        .withId(1l)
                        .withAllocation(2000)
                        .withUsed(750)
                        .create();
    }
}
