package galko.budgets.persistency.api.query;

import galko.budgets.persistency.api.InsertResult;
import galko.budgets.persistency.api.dto.BillDbo;
import java.time.ZonedDateTime;
import java.util.Collection;

public interface IBillDba {
    Collection<BillDbo> getBillByBudgetIdWithEndDateGreaterThan(long budgetId, ZonedDateTime minEndDate);
    void update(BillDbo dbObj);
    InsertResult insert(BillDbo dbObj);
}
