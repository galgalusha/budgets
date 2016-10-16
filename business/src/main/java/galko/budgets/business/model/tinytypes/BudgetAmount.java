package galko.budgets.business.model.tinytypes;

public class BudgetAmount {

    public BudgetAmount(int value) {
        this.value = value;
    }

    public final int value;

    public static BudgetAmount of(int value) {
        return new BudgetAmount(value);
    }
}
