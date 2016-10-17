package galko.budgets.business.model;

import galko.budgets.business.model.tinytypes.*;
import galko.budgets.persistency.api.dto.BillDbo;

public class Bill {

    public Bill(UserId userId, BudgetId budgetId, StartDate startDate, EndDate endDate, BillAmount billAmount) {
        this.userId = userId;
        this.budgetId = budgetId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.billAmount = billAmount;
    }

    public Bill(BillDbo dbObj) {
        this(UserId.of(dbObj.userId),
             BudgetId.of(dbObj.budgetId),
             StartDate.of(dbObj.startDate),
             EndDate.of(dbObj.endDate),
             BillAmount.of(dbObj.billAmount));
    }

    public final UserId userId;
    public final BudgetId budgetId;
    public final StartDate startDate;
    public final EndDate endDate;
    public final BillAmount billAmount;
}
