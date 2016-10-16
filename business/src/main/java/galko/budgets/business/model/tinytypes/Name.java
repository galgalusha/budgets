package galko.budgets.business.model.tinytypes;

public class Name {

    public Name(String value) {
        this.value = value;
    }

    public final String value;

    public static Name of(String value) {
        return new Name(value);
    }
}
