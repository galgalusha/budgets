package galko.budgets.business.api.db.query;

import galko.budgets.business.api.db.dto.BillDbo;
import galko.budgets.business.model.tinytypes.EndDate;
import galko.budgets.business.model.tinytypes.UserId;
import java.util.Collection;

public interface IBillDba {
    Collection<BillDbo> getBillsWithEndDateGreaterThan(UserId id, EndDate minEndDate);
}
