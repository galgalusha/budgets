package galko.budgets.business.model;

import galko.budgets.business.api.os.ITimeService;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

public class Monthly extends TimePeriod {

    protected Monthly(ITimeService timeService) {
        super(timeService);
    }

    @Override
    public Date beginningOfcurrent() {
        ZonedDateTime nowUtc = ZonedDateTime.ofInstant(timeService.getCurrentDateUtc().toInstant(), ZoneOffset.UTC);
        return Date.from(nowUtc.withDayOfMonth(1).toInstant());
    }

    @Override
    public Date endOfcurrent() {
        ZonedDateTime nowUtc = ZonedDateTime.ofInstant(timeService.getCurrentDateUtc().toInstant(), ZoneOffset.UTC);
        Calendar calendar = new Calendar.Builder().setInstant(timeService.getCurrentDateUtc()).build();
        return Date.from(nowUtc.withDayOfMonth(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)).toInstant());
    }
}
