package galko.budgets.persistency.api.query;

import galko.budgets.persistency.api.SaveResult;
import galko.budgets.persistency.api.dto.BillDbo;
import java.util.Collection;
import java.util.Date;

public interface IBillDba {
    Collection<BillDbo> getBillsWithEndDateGreaterThan(String userId, Date minEndDate);
    SaveResult save(BillDbo dbObj);
}
