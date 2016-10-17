package galko.budgets.business.model;

import galko.budgets.business.api.db.query.IBillDba;
import galko.budgets.business.api.db.query.IBudgetDba;
import galko.budgets.business.api.os.ITimeService;
import galko.budgets.business.model.tinytypes.EndDate;
import galko.budgets.business.model.tinytypes.UserId;
import galko.service_locator.ServiceLocator;
import java.util.Collection;
import static org.jooq.lambda.Seq.seq;

public class User {

    private final IBudgetDba budgetDba;
    private final IBillDba billDba;
    private final ITimeService timeService;
    public final UserId id;

    public User(ServiceLocator serviceLocator, UserId id) {
        this.budgetDba = serviceLocator.resolve(IBudgetDba.class);
        this.billDba = serviceLocator.resolve(IBillDba.class);
        this.timeService = serviceLocator.resolve(ITimeService.class);
        this.id = id;
    }

    public Collection<Budget> getBudgets() {
        return seq(budgetDba.getForUser(this.id))
                .map(Budget::new)
                .toList();
    }

    public Collection<Bill> getActiveBills() {

        final EndDate minEndDate = EndDate.of(timeService.getCurrentDateUtc());

        return seq(billDba.getBillsWithEndDateGreaterThan(this.id, minEndDate))
                .map(Bill::new)
                .toList();
    }
}
