package galko.budgets.web.service_locator;

import galko.budgets.MemoryDbSetup;
import galko.budgets.business.api.os.ITimeService;
import galko.budgets.persistency.api.query.IBillDba;
import galko.budgets.persistency.api.query.IBudgetDba;
import galko.service_locator.ServiceLocator;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

public class ServiceLocatorSetup {

    public final static ServiceLocator serviceLocator = new ServiceLocator();

    static {
        serviceLocator.register(IBillDba.class, MemoryDbSetup.db.getBillDba());
        serviceLocator.register(IBudgetDba.class, MemoryDbSetup.db.getBudgetDba());
        serviceLocator.register(ITimeService.class, new TimeService());
    }

    private static class TimeService implements ITimeService {
        @Override
        public Date getCurrentDateUtc() {
            return Date.from(ZonedDateTime.now(ZoneOffset.UTC).toInstant());
        }
    }
}
