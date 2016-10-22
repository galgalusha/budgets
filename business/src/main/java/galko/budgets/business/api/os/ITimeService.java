package galko.budgets.business.api.os;

import java.time.ZonedDateTime;

public interface ITimeService {
    ZonedDateTime getCurrent();
}
