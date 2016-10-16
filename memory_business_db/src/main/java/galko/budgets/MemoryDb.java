package galko.budgets;

import galko.budgets.business.api.db.IDbServices;
import galko.budgets.business.api.db.query.IBillDba;
import galko.budgets.business.api.db.query.IBudgetDba;

public class MemoryDb implements IDbServices {

    @Override
    public IBillDba getBillDba() {
        return new MemoryBillDba();
    }

    @Override
    public IBudgetDba getBudgetDba() {
        return new MemoryBudgetDba();
    }
}
