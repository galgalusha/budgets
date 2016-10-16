package galko.budgets.business.model;

import galko.budgets.business.api.db.dto.BudgetDbo;
import galko.budgets.business.model.tinytypes.Id;
import galko.budgets.business.model.tinytypes.BudgetAmount;
import galko.budgets.business.model.tinytypes.Name;
import galko.budgets.business.model.tinytypes.UserId;

public class Budget {

    public Budget(Id id, UserId userId, Name name, BudgetAmount amount, TimePeriod period) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.amount = amount;
        this.period = period;
    }

    public Budget(BudgetDbo dbObj) {
        this(dbObj.id, dbObj.userId, dbObj.name, dbObj.amount, dbObj.period);
    }

    public final Id id;
    public final UserId userId;
    public final Name name;
    public final BudgetAmount amount;
    public final TimePeriod period;
}
