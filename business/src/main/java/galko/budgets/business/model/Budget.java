package galko.budgets.business.model;

import galko.budgets.business.api.os.ITimeService;
import galko.budgets.business.model.tinytypes.Id;
import galko.budgets.business.model.tinytypes.BudgetAmount;
import galko.budgets.business.model.tinytypes.Name;
import galko.budgets.business.model.tinytypes.UserId;
import galko.budgets.persistency.api.dto.BudgetDbo;
import galko.service_locator.ServiceLocator;

public class Budget {

    private final ServiceLocator serviceLocator;
    private final ITimeService timeService;

    public Budget(ServiceLocator serviceLocator, Id id, UserId userId, Name name, BudgetAmount amount, TimePeriod period) {
        this.serviceLocator = serviceLocator;
        this.timeService = serviceLocator.resolve(ITimeService.class);
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.amount = amount;
        this.period = period;
    }

    public Budget(ServiceLocator serviceLocator, BudgetDbo dbObj) {
        this(serviceLocator,
                Id.of(dbObj.id),
                UserId.of(dbObj.userId),
                Name.of(dbObj.name),
                BudgetAmount.of(dbObj.amount),
                TimePeriod.fromDbo(serviceLocator.resolve(ITimeService.class), dbObj.period));
    }

    public final Id id;
    public final UserId userId;
    public final Name name;
    public final BudgetAmount amount;
    public final TimePeriod period;

    public Bill getActiveBill() {
        return null;
    }
}
