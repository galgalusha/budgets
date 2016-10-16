package galko.budgets.business.model.tinytypes;

public class Id {

    public Id(long value) {
        this.value = value;
    }

    public final long value;

    public static Id of(long value) {
        return new Id(value);
    }
}
