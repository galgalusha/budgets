package galko.budgets.business.api.db.query;

import galko.budgets.business.api.db.dto.BudgetDbo;
import galko.budgets.business.model.tinytypes.UserId;
import java.util.Collection;

public interface IBudgetDba {
    Collection<BudgetDbo> getForUser(UserId id);
}
