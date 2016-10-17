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
import org.junit.Before;
import org.junit.Test;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
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
    public void testGetActiveBill() {

        User user = new User(serviceLocator, UserId.of("galkoren"));

        billDba.bills.add(new BillDbo() {{
            userId = "galkoren";
            budgetId = 1l;
            endDate = Date.from(ZonedDateTime.now(ZoneOffset.UTC).plusDays(1).toInstant());
            startDate = Date.from(ZonedDateTime.now(ZoneOffset.UTC).minusDays(1).toInstant());
            billAmount = 1500;
        }});

        Collection<Bill> bills = user.getActiveBills();

        assertThat(bills, hasSize(1));

        Bill bill = bills.iterator().next();

        assertThat(bill.billAmount.value, equalTo(1500));
    }

    @Test
    public void testDontGetExpiredBill() {

        User user = new User(serviceLocator, UserId.of("galkoren"));

        billDba.bills.add(new BillDbo() {{
            userId = "galkoren";
            budgetId = 1l;
            endDate = Date.from(ZonedDateTime.now(ZoneOffset.UTC).minusDays(1).toInstant());
            startDate = Date.from(ZonedDateTime.now(ZoneOffset.UTC).minusDays(2).toInstant());
            billAmount = 1500;
        }});

        Collection<Bill> bills = user.getActiveBills();

        assertThat(bills, empty());
    }

}
