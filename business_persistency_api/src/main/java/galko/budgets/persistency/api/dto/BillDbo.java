package galko.budgets.persistency.api.dto;

import java.time.ZonedDateTime;

public class BillDbo {

    public long id;
    public String userId;
    public long budgetId;
    public ZonedDateTime startDate;
    public ZonedDateTime endDate;
    public int billAmount;
}
