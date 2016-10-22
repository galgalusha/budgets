package galko.budgets;

import galko.budgets.persistency.api.InsertResult;
import galko.budgets.persistency.api.dto.BillDbo;
import galko.budgets.persistency.api.query.IBillDba;
import java.util.*;
import static org.jooq.lambda.Seq.seq;

public class MemoryBillDba implements IBillDba {

    public List<BillDbo> bills = new LinkedList<>();

    @Override
    public Collection<BillDbo> getBillByBudgetIdWithEndDateGreaterThan(long budgetId, Date minEndDate) {
        return seq(bills)
                .filter(x -> x.budgetId == budgetId)
                .filter(x -> x.endDate.after(minEndDate))
                .toList();
    }

    @Override
    public void update(BillDbo dbObj) {
        BillDbo existingBill = seq(bills).findFirst(bill -> bill.id == dbObj.id).get();
        bills.remove(existingBill);
        bills.add(dbObj);
    }

    @Override
    public InsertResult insert(BillDbo dbObj) {

        Optional<BillDbo> existingBill = seq(bills).findFirst(bill -> bill.id == dbObj.id);

        if (existingBill.isPresent()) {
            throw new RuntimeException("Bill already exists. ID: " + dbObj.id);
        }

        bills.add(dbObj);
        dbObj.id = findMaxId() + 1;
        return new InsertResult(dbObj.id);
    }


    private long findMaxId() {
        return seq(bills).map(bill -> bill.id).max().orElse(0l);
    }
}
