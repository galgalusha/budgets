package galko.budgets.business.model;

import galko.budgets.business.model.tinytypes.UserId;
import galko.budgets.persistency.api.query.IBudgetDba;
import galko.service_locator.ServiceLocator;
import java.util.Collection;
import static org.jooq.lambda.Seq.seq;

public class User {

    private final ServiceLocator serviceLocator;
    private final IBudgetDba budgetDba;
    public final UserId id;

    public User(ServiceLocator serviceLocator, UserId id) {
        this.serviceLocator = serviceLocator;
        this.budgetDba = serviceLocator.resolve(IBudgetDba.class);
        this.id = id;
    }

    public Collection<Budget> getBudgets() {
        return seq(budgetDba.getForUser(this.id.value))
                .map(dbo -> new Budget(serviceLocator, dbo))
                .toList();
    }
}
