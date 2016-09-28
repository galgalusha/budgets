package galko.budgets.web.infra.rest;

public class StartingAfterPrefix {
    public final String value;

    public StartingAfterPrefix(String value) {
        this.value = value;
    }

    public static StartingAfterPrefix of(String value) {
        return new StartingAfterPrefix(value);
    }
}
