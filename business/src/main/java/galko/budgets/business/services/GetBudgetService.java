package galko.budgets.business.services;

import galko.budgets.business.api.web.IService;
import galko.budgets.business.api.web.dto.Budget;
import galko.budgets.business.api.web.dto.Nothing;

import java.util.Arrays;
import java.util.List;

public class GetBudgetService implements IService<Integer, Budget> {

    public Budget handle(Integer budgetId) {
        return Budget.config()
                .withName("Groceries")
                .withId(1l)
                .withAllocation(2000)
                .withUsed(750)
                .create();
    }

}
