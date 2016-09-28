package galko.budgets.business.services;

import galko.budgets.business.api.web.IService;
import galko.budgets.business.api.web.dto.Budget;
import galko.budgets.business.api.web.dto.Nothing;

import java.util.Arrays;
import java.util.List;

public class GetAllBudgetsService implements IService<Nothing, List<Budget>> {

    public List<Budget> handle(Nothing nothing) {
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
