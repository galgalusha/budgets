package galko.budgets.business.model.tinytypes;

import java.time.ZonedDateTime;

public class StartDate {

    public StartDate(ZonedDateTime value) {
        this.value = value;
    }

    public final ZonedDateTime value;

    public static StartDate of(ZonedDateTime value) {
        return new StartDate(value);
    }
}
