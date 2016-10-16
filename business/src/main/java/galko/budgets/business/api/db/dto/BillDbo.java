package galko.budgets.business.api.db.dto;

import galko.budgets.business.model.tinytypes.*;

public class BillDbo {

    public BillDbo(UserId userId, BudgetId budgetId, StartDate startDate, EndDate endDate, BillAmount billAmount) {
        this.userId = userId;
        this.budgetId = budgetId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.billAmount = billAmount;
    }

    public final UserId userId;
    public final BudgetId budgetId;
    public final StartDate startDate;
    public final EndDate endDate;
    public final BillAmount billAmount;
}
