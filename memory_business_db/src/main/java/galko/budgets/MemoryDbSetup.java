package galko.budgets;

import galko.budgets.business.api.db.dto.BillDbo;
import galko.budgets.business.api.db.dto.BudgetDbo;
import galko.budgets.business.model.TimePeriod;
import galko.budgets.business.model.tinytypes.*;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public class MemoryDbSetup {

    public static MemoryDb db = new MemoryDb();

    static {

        final List<BudgetDbo> budgets = ((MemoryBudgetDba) db.getBudgetDba()).budgets;
        final List<BillDbo> bills = ((MemoryBillDba) db.getBillDba()).bills;
        final String userId = "facebook|10210336859600830";

        budgets.add(new BudgetDbo(
                Id.of(1l),
                UserId.of(userId),
                Name.of("Groceries New"),
                BudgetAmount.of(2000),
                TimePeriod.Month));

        budgets.add(new BudgetDbo(
                Id.of(2l),
                UserId.of(userId),
                Name.of("Electrics New"),
                BudgetAmount.of(800),
                TimePeriod.Month));

        budgets.add(new BudgetDbo(
                Id.of(3l),
                UserId.of(userId),
                Name.of("Dog New"),
                BudgetAmount.of(200),
                TimePeriod.Month));


        bills.add(new BillDbo(
                UserId.of(userId),
                BudgetId.of(1l),
                StartDate.of(Date.from(ZonedDateTime.now(ZoneOffset.UTC).minusDays(1).toInstant())),
                EndDate.of(Date.from(ZonedDateTime.now(ZoneOffset.UTC).plusDays(1).toInstant())),
                BillAmount.of(300)));

        bills.add(new BillDbo(
                UserId.of(userId),
                BudgetId.of(2l),
                StartDate.of(Date.from(ZonedDateTime.now(ZoneOffset.UTC).minusDays(1).toInstant())),
                EndDate.of(Date.from(ZonedDateTime.now(ZoneOffset.UTC).plusDays(1).toInstant())),
                BillAmount.of(2500)));

        bills.add(new BillDbo(
                UserId.of(userId),
                BudgetId.of(3l),
                StartDate.of(Date.from(ZonedDateTime.now(ZoneOffset.UTC).minusDays(1).toInstant())),
                EndDate.of(Date.from(ZonedDateTime.now(ZoneOffset.UTC).plusDays(1).toInstant())),
                BillAmount.of(0)));
    }
}
