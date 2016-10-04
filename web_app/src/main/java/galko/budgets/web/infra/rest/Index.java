package galko.budgets.web.infra.rest;

public class Index {
    public final int value;

    public Index(int value) {
        this.value = value;
    }

    public static Index of(int value) {
        return new Index(value);
    }
}