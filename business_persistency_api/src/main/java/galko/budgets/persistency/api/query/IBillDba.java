package galko.budgets.persistency.api.query;

import galko.budgets.persistency.api.InsertResult;
import galko.budgets.persistency.api.dto.BillDbo;
import java.util.Collection;
import java.util.Date;

public interface IBillDba {
    Collection<BillDbo> getBillByBudgetIdWithEndDateGreaterThan(long budgetId, Date minEndDate);
    void update(BillDbo dbObj);
    InsertResult insert(BillDbo dbObj);
}
