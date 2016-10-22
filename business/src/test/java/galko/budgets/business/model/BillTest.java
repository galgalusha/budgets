package galko.budgets.business.model;

import galko.budgets.MemoryBillDba;
import galko.budgets.business.model.tinytypes.BillAmount;
import galko.budgets.business.model.tinytypes.Id;
import galko.budgets.persistency.api.dto.BillDbo;
import galko.budgets.persistency.api.query.IBillDba;
import galko.service_locator.ServiceLocator;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.jooq.lambda.Seq.seq;
import static org.junit.Assert.assertThat;

public class BillTest {

    private ServiceLocator serviceLocator = new ServiceLocator();
    private MemoryBillDba billDba = new MemoryBillDba();

    @Before
    public void setup() {
        serviceLocator.register(IBillDba.class, billDba);
    }

    @Test
    public void addExpenseShouldUpdateDatabase() {

        setupBillsInDb(billDbo(Id.of(1l), BillAmount.of(500)));

        final Bill initialBill = new Bill(serviceLocator, fromDb(Id.of(1l)));

        initialBill.addExpense(BillAmount.of(100));

        final BillDbo newBillInDb = fromDb(Id.of(1l));

        assertThat(billDba.bills, hasSize(1));
        assertThat(newBillInDb.billAmount, equalTo(600));
        assertThat(newBillInDb.id, equalTo(1l));
    }

    @Test
    public void addExpenseReturnsNewBill() {

        setupBillsInDb(billDbo(Id.of(1l), BillAmount.of(500)));

        final Bill initialBill = new Bill(serviceLocator, fromDb(Id.of(1l)));

        final Bill resultBill = initialBill.addExpense(BillAmount.of(100));

        assertThat(resultBill.id.getValue(), equalTo(1l));
        assertThat(resultBill.billAmount.value, equalTo(600));
    }

    private void setupBillsInDb(BillDbo dbo) {
        billDba.bills.clear();
        billDba.bills.add(dbo);
    }

    private BillDbo billDbo(Id idArg, BillAmount amount) {
        return new BillDbo() {{
            billAmount = amount.value;
            id = idArg.getValue();
        }};
    }

    private BillDbo fromDb(Id idArg) {
        return seq(billDba.bills).findFirst(bill -> bill.id == idArg.getValue()).get();
    }
}
