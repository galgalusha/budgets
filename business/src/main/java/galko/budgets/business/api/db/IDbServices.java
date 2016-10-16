package galko.budgets.business.api.db;

import galko.budgets.business.api.db.query.IBillDba;
import galko.budgets.business.api.db.query.IBudgetDba;

public interface IDbServices {
    IBillDba getBillDba();
    IBudgetDba getBudgetDba();
}
