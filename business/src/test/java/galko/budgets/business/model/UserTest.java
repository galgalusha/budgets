package galko.budgets.business.model;

import galko.budgets.MemoryBillDba;
import galko.budgets.MemoryBudgetDba;
import galko.budgets.business.api.os.ITimeService;
import galko.budgets.business.model.tinytypes.UserId;
import galko.budgets.persistency.api.dto.BillDbo;
import galko.budgets.persistency.api.dto.BudgetDbo;
import galko.budgets.persistency.api.query.IBillDba;
import galko.budgets.persistency.api.query.IBudgetDba;
import galko.service_locator.ServiceLocator;
import org.jooq.lambda.Seq;
import org.junit.Before;
import org.junit.Test;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.jooq.lambda.Seq.seq;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserTest {

    private MemoryBudgetDba budgetDba = new MemoryBudgetDba();
    private MemoryBillDba billDba = new MemoryBillDba();
    private ITimeService timeServiceMock = mock(ITimeService.class);
    private ServiceLocator serviceLocator = new ServiceLocator();

    @Before
    public void setup() {

        serviceLocator.register(IBudgetDba.class, budgetDba);
        serviceLocator.register(IBillDba.class, billDba);
        serviceLocator.register(ITimeService.class, timeServiceMock);

        when(timeServiceMock.getCurrentDateUtc()).thenReturn(Date.from(ZonedDateTime.now(ZoneOffset.UTC).toInstant()));
    }

    @Test
    public void testGetBudgets() {

        User user = new User(serviceLocator, UserId.of("galkoren"));

        budgetDba.budgets.add(new BudgetDbo() {{
            id = 1l;
            userId = "galkoren";
            name = "groceries";
            amount = 2000;
            period = galko.budgets.persistency.api.dto.TimePeriod.Month;
        }});

        Collection<Budget> budgets = user.getBudgets();

        assertThat(budgets, hasSize(1));

        Budget budget = budgets.iterator().next();

        assertThat(budget.name.value, equalTo("groceries"));
    }

    @Test
    public void testGetExistingActiveBill() {

        User user = new User(serviceLocator, UserId.of("galkoren"));

        budgetDba.budgets.add(new BudgetDbo() {{
            id = 1l;
            userId = "galkoren";
            name = "groceries";
            amount = 2000;
            period = galko.budgets.persistency.api.dto.TimePeriod.Month;
        }});

        billDba.bills.add(new BillDbo() {{
            id = 100l;
            userId = "galkoren";
            budgetId = 1l;
            endDate = Date.from(ZonedDateTime.now(ZoneOffset.UTC).plusDays(1).toInstant());
            startDate = Date.from(ZonedDateTime.now(ZoneOffset.UTC).minusDays(1).toInstant());
            billAmount = 1500;
        }});

        Collection<Bill> bills = user.getActiveBills();

        assertThat(bills, hasSize(1));

        Bill bill = bills.iterator().next();

        assertThat(bill.id.value, equalTo(100l));
    }

    @Test
    public void testDontGetExpiredBill() {

        User user = new User(serviceLocator, UserId.of("galkoren"));

        budgetDba.budgets.add(new BudgetDbo() {{
            id = 1l;
            userId = "galkoren";
            name = "groceries";
            amount = 2000;
            period = galko.budgets.persistency.api.dto.TimePeriod.Month;
        }});

        BillDbo expired1 = new BillDbo() {{
            id = 100l;
            userId = "galkoren";
            budgetId = 1l;
            endDate = Date.from(ZonedDateTime.now(ZoneOffset.UTC).minusDays(1).toInstant());
            startDate = Date.from(ZonedDateTime.now(ZoneOffset.UTC).minusDays(2).toInstant());
            billAmount = 1500;
        }};

        BillDbo expired2 = new BillDbo() {{
            id = 103l;
            userId = "galkoren";
            budgetId = 1l;
            endDate = Date.from(ZonedDateTime.now(ZoneOffset.UTC).minusDays(1).toInstant());
            startDate = Date.from(ZonedDateTime.now(ZoneOffset.UTC).minusDays(2).toInstant());
            billAmount = 1500;
        }};

        BillDbo active = new BillDbo() {{
            id = 102l;
            userId = "galkoren";
            budgetId = 1l;
            endDate = Date.from(ZonedDateTime.now(ZoneOffset.UTC).plusDays(1).toInstant());
            startDate = Date.from(ZonedDateTime.now(ZoneOffset.UTC).minusDays(2).toInstant());
            billAmount = 1500;
        }};


        billDba.bills.addAll(Seq.of(expired1, active, expired2).toList());

        Collection<Bill> bills = user.getActiveBills();

        assertThat(bills, hasSize(1));

        Bill bill = bills.iterator().next();

        assertThat(bill.id.value, equalTo(active.id));
    }

    @Test
    public void testCreateMissingActiveBillWhenThereIsNoBillHistory() {

        User user = new User(serviceLocator, UserId.of("galkoren"));

        budgetDba.budgets.add(new BudgetDbo() {{
            id = 1l;
            userId = "galkoren";
            name = "groceries";
            amount = 2000;
            period = galko.budgets.persistency.api.dto.TimePeriod.Month;
        }});

        Collection<Bill> bills = user.getActiveBills();

        assertThat(bills, hasSize(1));

        Bill returnedBill = bills.iterator().next();
        BillDbo addedToDb = seq(billDba.bills).findFirst(dbo -> dbo.id == returnedBill.id.value).get();

        assertThat(addedToDb.billAmount, equalTo(0));
        assertThat(addedToDb.budgetId, equalTo(1l));
        assertThat(addedToDb.userId, equalTo("galkoren"));
        assertThat(addedToDb.startDate, equalTo(timeServiceMock.getCurrentDateUtc()));

        // TODO:
        // assertThat(addedToDb.endDate, equalTo( next month
        //
    }

}
