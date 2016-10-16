package galko.budgets.business.model.tinytypes;

public class BudgetId {

    public BudgetId(long value) {
        this.value = value;
    }

    public final long value;

    public static BudgetId of(long value) {
        return new BudgetId(value);
    }
}
