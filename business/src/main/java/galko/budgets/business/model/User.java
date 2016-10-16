package galko.budgets.business.model;

import galko.budgets.business.api.db.IDbServices;
import galko.budgets.business.api.os.ITimeService;
import galko.budgets.business.model.tinytypes.EndDate;
import galko.budgets.business.model.tinytypes.UserId;
import java.util.Collection;
import static org.jooq.lambda.Seq.seq;

public class User {

    private final IDbServices dbServices;
    private final ITimeService timeService;
    public final UserId id;

    public User(IDbServices dbServices, ITimeService timeService, UserId id) {
        this.dbServices = dbServices;
        this.timeService = timeService;
        this.id = id;
    }

    public Collection<Budget> getBudgets() {
        return seq(dbServices.getBudgetDba().getForUser(this.id))
                .map(Budget::new)
                .toList();
    }

    public Collection<Bill> getActiveBills() {

        final EndDate minEndDate = EndDate.of(timeService.getCurrentDateUtc());

        return seq(dbServices.getBillDba().getBillsWithEndDateGreaterThan(this.id, minEndDate))
                .map(Bill::new)
                .toList();
    }
}
