package galko.budgets.business.model;

import galko.budgets.business.api.os.ITimeService;
import java.time.ZonedDateTime;

public class Monthly extends TimePeriod {

    public Monthly(ITimeService timeService) {
        super(timeService);
    }

    @Override
    public ZonedDateTime beginningOfcurrent() {
        return timeService.getCurrent().withDayOfMonth(1);
    }

    @Override
    public ZonedDateTime endOfcurrent() {
        final ZonedDateTime now = timeService.getCurrent();
        return now.withDayOfMonth(now.getMonth().maxLength());
    }

    @Override
    public galko.budgets.persistency.api.dto.TimePeriod toDbo() {
        return galko.budgets.persistency.api.dto.TimePeriod.Month;
    }
}
