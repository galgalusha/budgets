package galko.budgets.business.model.tinytypes;

public class UserId {

    public UserId(String value) {
        this.value = value;
    }

    public final String value;

    public static UserId of(String value) {
        return new UserId(value);
    }
}
