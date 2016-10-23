package galko.budgets.web.service_locator;

import galko.budgets.MemoryDbSetup;
import galko.budgets.business.api.os.ITimeService;
import galko.budgets.persistency.api.query.IBillDba;
import galko.budgets.persistency.api.query.IBudgetDba;
import galko.service_locator.ServiceLocator;
import java.time.ZonedDateTime;

public class ServiceLocatorSetup {

    public final static ServiceLocator serviceLocator = new ServiceLocator();

    static {
        serviceLocator.register(IBillDba.class, MemoryDbSetup.db.billDba);
        serviceLocator.register(IBudgetDba.class, MemoryDbSetup.db.budgetDba);
        serviceLocator.register(ITimeService.class, new TimeService());
    }

    private static class TimeService implements ITimeService {
        @Override
        public ZonedDateTime getCurrent() {
            return ZonedDateTime.now();
        }
    }
}
