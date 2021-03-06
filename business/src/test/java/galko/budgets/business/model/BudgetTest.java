package galko.budgets.business.model;

import galko.budgets.MemoryBillDba;
import galko.budgets.MemoryBudgetDba;
import galko.budgets.business.api.os.ITimeService;
import galko.budgets.business.model.tinytypes.*;
import galko.budgets.persistency.api.dto.BillDbo;
import galko.budgets.persistency.api.query.IBillDba;
import galko.budgets.persistency.api.query.IBudgetDba;
import galko.service_locator.ServiceLocator;
import org.jooq.lambda.Seq;
import org.junit.Before;
import org.junit.Test;
import java.time.ZonedDateTime;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.jooq.lambda.Seq.seq;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BudgetTest {

    private MemoryBudgetDba budgetDba = new MemoryBudgetDba();
    private MemoryBillDba billDba = new MemoryBillDba();
    private ITimeService timeServiceMock = mock(ITimeService.class);
    private ServiceLocator serviceLocator = new ServiceLocator();

    @Before
    public void setup() {

        serviceLocator.register(IBudgetDba.class, budgetDba);
        serviceLocator.register(IBillDba.class, billDba);
        serviceLocator.register(ITimeService.class, timeServiceMock);

        when(timeServiceMock.getCurrent()).thenReturn(ZonedDateTime.now());
    }

    private Budget defaultBudget() {
        return new Budget(
                serviceLocator,
                Id.of(1l),
                UserId.of("galkoren"),
                Name.of("groceries"),
                BudgetAmount.of(1000),
                new Monthly(timeServiceMock));
    }

    private final static ZonedDateTime Yesterday = ZonedDateTime.now().minusDays(1);

    private final static ZonedDateTime TwoDaysAgo = ZonedDateTime.now().minusDays(2);

    private final static ZonedDateTime Tomorrow = ZonedDateTime.now().plusDays(1);

    private final static BillDbo[] NoBills = new BillDbo[0];


    @Test
    public void getActiveBillReturnsTheOnlyBillThatIsNotExpiredFromTheBillHistory() {

        final Budget budget = defaultBudget();

        setupBillsInDb(
                billDbo(budget, Id.of(10l), StartDate.of(TwoDaysAgo), EndDate.of(Yesterday)),
                billDbo(budget, Id.of(11l), StartDate.of(TwoDaysAgo), EndDate.of(Tomorrow)),
                billDbo(budget, Id.of(12l), StartDate.of(TwoDaysAgo), EndDate.of(Yesterday))
        );

        final Bill activeBill = budget.getActiveBill();

        assertThat(activeBill.id.getValue(), equalTo(11l));
    }

    @Test
    public void getActiveBillCreatesNewBillWithZeroAmount() {

        final Budget budget = defaultBudget();

        setupBillsInDb(NoBills);

        final Bill activeBill = budget.getActiveBill();

        assertThat(activeBill.billAmount.value, equalTo(0));
    }

    @Test
    public void getActiveBillCreatesNewBillWithBudgetAndUserIds() {

        final Budget budget = defaultBudget();

        setupBillsInDb(NoBills);

        final Bill activeBill = budget.getActiveBill();

        assertThat(activeBill.budgetId.value, equalTo(budget.id.getValue()));
        assertThat(activeBill.userId.value, equalTo(budget.userId.value));
    }

    @Test
    public void when_GetActiveBillCreatesNewBill_then_ItHasStartAndEndDate() {

        final Budget budget = defaultBudget();

        setupBillsInDb(NoBills);

        final Bill activeBill = budget.getActiveBill();

        assertThat(activeBill.startDate.value, equalTo(budget.period.beginningOfcurrent()));
        assertThat(activeBill.endDate.value, equalTo(budget.period.endOfcurrent()));
    }


    @Test
    public void getActiveBillCreatesNewBillWhenAllBillsExpired() {

        final Budget budget = defaultBudget();

        setupBillsInDb(
                billDbo(budget, Id.of(1000l), StartDate.of(TwoDaysAgo), EndDate.of(Yesterday))
        );

        final Bill activeBill = budget.getActiveBill();

        final BillDbo newInDb = seq(billDba.bills).findFirst(bill -> bill.id != 1000l).get();

        assertThat(activeBill.id.getValue(), equalTo(newInDb.id));
    }


    @Test
    public void getActiveBillCreatesNewBillWhenThereIsNoBillHistory() {

        final Budget budget = defaultBudget();

        setupBillsInDb(NoBills);

        final Bill activeBill = budget.getActiveBill();

        final BillDbo newInDb = billDba.bills.get(0);

        assertThat(activeBill.id.getValue(), equalTo(newInDb.id));
    }

    private BillDbo billDbo(Budget budget, Id idArg, StartDate start, EndDate end) {
        return new BillDbo() {{
            id = idArg.getValue();
            userId = budget.userId.value;
            budgetId = budget.id.getValue();
            startDate = start.value;
            endDate = end.value;
        }};
    }

    private void setupBillsInDb(BillDbo... bills) {
        billDba.bills = Seq.of(bills).toList();
    }
}
