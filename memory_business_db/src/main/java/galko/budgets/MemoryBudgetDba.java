package galko.budgets;

import galko.budgets.persistency.api.InsertResult;
import galko.budgets.persistency.api.dto.BudgetDbo;
import galko.budgets.persistency.api.query.IBudgetDba;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import static org.jooq.lambda.Seq.seq;

public class MemoryBudgetDba implements IBudgetDba {

    public List<BudgetDbo> budgets = new LinkedList<>();

    @Override
    public Collection<BudgetDbo> getForUser(String userIid) {
        return seq(budgets)
                .filter(x -> x.userId.equals(userIid))
                .toList();
    }

    @Override
    public Optional<BudgetDbo> getById(long id) {
        return seq(budgets).findFirst(budget -> budget.id == id);
    }

    @Override
    public InsertResult insert(BudgetDbo budget) {
        budget.id = findMaxId() + 1;
        budgets.add(budget);
        return new InsertResult(budget.id);
    }

    @Override
    public void update(BudgetDbo budget) {
        throw new UnsupportedOperationException("update method not yet implemented");
    }

    private long findMaxId() {
        return seq(budgets).map(x -> x.id).max().orElse(0l);
    }

}
