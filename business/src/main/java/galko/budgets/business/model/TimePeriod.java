package galko.budgets.business.model;

import galko.budgets.business.api.os.ITimeService;

import java.time.ZonedDateTime;

public abstract class TimePeriod {

    protected final ITimeService timeService;

    protected TimePeriod(ITimeService timeService) {
        this.timeService = timeService;
    }

    public abstract ZonedDateTime beginningOfcurrent();

    public abstract ZonedDateTime endOfcurrent();

    public static TimePeriod fromDbo(
            ITimeService timeService,
            galko.budgets.persistency.api.dto.TimePeriod dbValue) {

        switch (dbValue) {
            case Month: return new Monthly(timeService);
            default: throw new IllegalArgumentException("Unsupported TimePeriod: " + dbValue);
        }

    }

    public abstract galko.budgets.persistency.api.dto.TimePeriod toDbo();
}
