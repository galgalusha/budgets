package galko.budgets.persistency.api.query;

import galko.budgets.persistency.api.dto.BudgetDbo;
import java.util.Collection;

public interface IBudgetDba {
    Collection<BudgetDbo> getForUser(String userId);
}
