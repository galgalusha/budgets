package galko.budgets.persistency.api.dto;

import java.util.Date;

public class BillDbo {

    public long id;
    public String userId;
    public long budgetId;
    public Date startDate;
    public Date endDate;
    public int billAmount;
}
