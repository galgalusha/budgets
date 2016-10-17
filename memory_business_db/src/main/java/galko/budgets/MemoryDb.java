package galko.budgets;


import galko.budgets.persistency.api.query.IBillDba;
import galko.budgets.persistency.api.query.IBudgetDba;

public class MemoryDb {

    public IBillDba getBillDba() {
        return new MemoryBillDba();
    }

    public IBudgetDba getBudgetDba() {
        return new MemoryBudgetDba();
    }
}
