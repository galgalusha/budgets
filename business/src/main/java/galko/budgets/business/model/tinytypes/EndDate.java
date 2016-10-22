package galko.budgets.business.model.tinytypes;

import java.time.ZonedDateTime;

public class EndDate {

    public EndDate(ZonedDateTime value) {
        this.value = value;
    }

    public final ZonedDateTime value;

    public static EndDate of(ZonedDateTime value) {
        return new EndDate(value);
    }
}
