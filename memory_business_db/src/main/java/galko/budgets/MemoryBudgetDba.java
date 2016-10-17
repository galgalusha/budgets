package galko.budgets;

import galko.budgets.persistency.api.dto.BudgetDbo;
import galko.budgets.persistency.api.query.IBudgetDba;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.jooq.lambda.Seq.seq;

public class MemoryBudgetDba implements IBudgetDba {

    public List<BudgetDbo> budgets = new LinkedList<>();

    @Override
    public Collection<BudgetDbo> getForUser(String userIid) {
        return seq(budgets)
                .filter(x -> x.userId.equals(userIid))
                .toList();
    }
}
