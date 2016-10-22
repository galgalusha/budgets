package galko.budgets;

import galko.budgets.persistency.api.dto.BillDbo;
import galko.budgets.persistency.api.dto.BudgetDbo;
import java.time.ZonedDateTime;
import java.util.List;

public class MemoryDbSetup {

    public static MemoryDb db = new MemoryDb();

    static {

        final List<BudgetDbo> budgets = ((MemoryBudgetDba) db.getBudgetDba()).budgets;
        final List<BillDbo> bills = ((MemoryBillDba) db.getBillDba()).bills;
        final String USER_ID = "facebook|10210336859600830";

        budgets.add(new BudgetDbo() {{
            id = 1l;
            userId = USER_ID;
            name = "groceries";
            amount = 2000;
            period = galko.budgets.persistency.api.dto.TimePeriod.Month;
        }});

        bills.add(new BillDbo() {{
            userId = USER_ID;
            budgetId = 1l;
            endDate = ZonedDateTime.now().plusDays(1);
            startDate = ZonedDateTime.now().minusDays(1);
            billAmount = 0;
        }});
    }
}
