package galko.budgets;

import galko.budgets.business.api.db.query.IBillDba;
import galko.budgets.business.api.db.query.IBudgetDba;

public class MemoryDb {

    public IBillDba getBillDba() {
        return new MemoryBillDba();
    }

    public IBudgetDba getBudgetDba() {
        return new MemoryBudgetDba();
    }
}
