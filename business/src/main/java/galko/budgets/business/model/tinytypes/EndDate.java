package galko.budgets.business.model.tinytypes;

import java.util.Date;

public class EndDate {

    public EndDate(Date value) {
        this.value = value;
    }

    public final Date value;

    public static EndDate of(Date value) {
        return new EndDate(value);
    }
}
