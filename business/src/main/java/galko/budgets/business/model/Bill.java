package galko.budgets.business.model;

import galko.budgets.business.model.tinytypes.*;
import galko.budgets.persistency.api.dto.BillDbo;
import galko.budgets.persistency.api.query.IBillDba;
import galko.service_locator.ServiceLocator;

public class Bill {

    public final Id id;
    public final UserId userId;
    public final BudgetId budgetId;
    public final StartDate startDate;
    public final EndDate endDate;
    public final BillAmount billAmount;
    private final IBillDba billDba;
    private final ServiceLocator serviceLocator;

    public Bill(ServiceLocator serviceLocator, Id id, UserId userId, BudgetId budgetId, StartDate startDate, EndDate endDate, BillAmount billAmount) {
        this.userId = userId;
        this.budgetId = budgetId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.billAmount = billAmount;
        this.billDba = serviceLocator.resolve(IBillDba.class);
        this.id = id;
        this.serviceLocator = serviceLocator;
    }

    public Bill(ServiceLocator serviceLocator, BillDbo dbObj) {
        this(serviceLocator,
                Id.of(dbObj.id),
                UserId.of(dbObj.userId),
                BudgetId.of(dbObj.budgetId),
                StartDate.of(dbObj.startDate),
                EndDate.of(dbObj.endDate),
                BillAmount.of(dbObj.billAmount));
    }

    private BillDbo toDbObject() {
        BillDbo dbObj = new BillDbo();
        dbObj.userId = this.userId.value;
        dbObj.budgetId = this.budgetId.value;
        dbObj.billAmount = this.billAmount.value;
        dbObj.endDate = this.endDate.value;
        dbObj.startDate = this.startDate.value;
        dbObj.id = this.id.value;
        return dbObj;
    }

    public Bill addExpense(BillAmount amount) {
        BillDbo dbObj = toDbObject();
        dbObj.billAmount += amount.value;
        billDba.save(dbObj);
        return new Bill(serviceLocator, dbObj);
    }
}
