package galko.budgets.business.api.web.services;

import galko.budgets.business.api.web.IService;
import galko.budgets.business.api.web.dto.ActiveBill;
import galko.budgets.business.api.web.dto.EmptyRequest;

import java.util.Arrays;
import java.util.List;

public class GetActiveBills implements IService<EmptyRequest, List<ActiveBill>> {

    public List<ActiveBill> handle(EmptyRequest emptyRequest) {
        return Arrays.asList(
                ActiveBill.config()
                        .withBudgetName("Groceries")
                        .withBudgetId(1l)
                        .withBudgetAmount(2000)
                        .withBillAmount(750)
                        .create()
                ,
                ActiveBill.config()
                        .withBudgetName("Dressing")
                        .withBudgetId(2l)
                        .withBudgetAmount(500)
                        .withBillAmount(0)
                        .create()
                ,
                ActiveBill.config()
                        .withBudgetName("Misc")
                        .withBudgetId(3l)
                        .withBudgetAmount(1500)
                        .withBillAmount(2000)
                        .create()
        );
    }

}
