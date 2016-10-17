package galko.budgets.persistency.api.query;

import galko.budgets.persistency.api.dto.BudgetDbo;
import java.util.Collection;
import java.util.Optional;

public interface IBudgetDba {
    Collection<BudgetDbo> getForUser(String userId);
    Optional<BudgetDbo> getById(long id);
}
