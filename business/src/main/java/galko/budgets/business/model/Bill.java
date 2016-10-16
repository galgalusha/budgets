package galko.budgets.business.model;

import galko.budgets.business.api.db.dto.BillDbo;
import galko.budgets.business.model.tinytypes.*;

public class Bill {

    public Bill(UserId userId, BudgetId budgetId, StartDate startDate, EndDate endDate, BillAmount billAmount) {
        this.userId = userId;
        this.budgetId = budgetId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.billAmount = billAmount;
    }

    public Bill(BillDbo dbObj) {
        this(dbObj.userId, dbObj.budgetId, dbObj.startDate, dbObj.endDate, dbObj.billAmount);
    }

    public final UserId userId;
    public final BudgetId budgetId;
    public final StartDate startDate;
    public final EndDate endDate;
    public final BillAmount billAmount;
}
