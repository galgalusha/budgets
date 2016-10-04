package galko.budgets.business.services;

import galko.budgets.business.api.web.IService;
import galko.budgets.business.api.web.dto.Budget;
import galko.budgets.business.api.web.dto.GetBudgetRequest;

public class GetBudgetService implements IService<GetBudgetRequest, Budget> {

    public Budget handle(GetBudgetRequest request) {
        return Budget.config()
                .withName("Groceries")
                .withId(1l)
                .withAllocation(2000)
                .withUsed(750)
                .create();
    }

}
