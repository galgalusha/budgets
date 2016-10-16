package galko.budgets.business.model.tinytypes;

public class BillAmount {

    public BillAmount(int value) {
        this.value = value;
    }

    public final int value;

    public static BillAmount of(int value) {
        return new BillAmount(value);
    }
}
