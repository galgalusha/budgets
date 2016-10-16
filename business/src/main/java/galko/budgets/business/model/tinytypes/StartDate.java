package galko.budgets.business.model.tinytypes;

import java.util.Date;

public class StartDate {

    public StartDate(Date value) {
        this.value = value;
    }

    public final Date value;

    public static StartDate of(Date value) {
        return new StartDate(value);
    }
}
