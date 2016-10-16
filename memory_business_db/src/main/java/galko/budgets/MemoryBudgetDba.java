package galko.budgets;

import galko.budgets.business.api.db.dto.BudgetDbo;
import galko.budgets.business.api.db.query.IBudgetDba;
import galko.budgets.business.model.tinytypes.UserId;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import static org.jooq.lambda.Seq.seq;

public class MemoryBudgetDba implements IBudgetDba {

    public List<BudgetDbo> budgets = new LinkedList<>();

    @Override
    public Collection<BudgetDbo> getForUser(UserId id) {
        return seq(budgets)
                .filter(x -> x.userId.value.equals(id.value))
                .toList();
    }
}
