package galko.budgets;

import galko.budgets.persistency.api.SaveResult;
import galko.budgets.persistency.api.dto.BillDbo;
import galko.budgets.persistency.api.query.IBillDba;
import java.util.*;
import static org.jooq.lambda.Seq.seq;

public class MemoryBillDba implements IBillDba {

    public List<BillDbo> bills = new LinkedList<>();

    @Override
    public Collection<BillDbo> getBillsWithEndDateGreaterThan(String userId, Date minEndDate) {
        return seq(bills)
                .filter(x -> x.userId.equals(userId))
                .filter(x -> x.endDate.after(minEndDate))
                .toList();
    }

    @Override
    public SaveResult save(BillDbo dbObj) {

        Optional<BillDbo> existingBill = seq(bills).findFirst(bill -> bill.id == dbObj.id);

        if (existingBill.isPresent()) {
            bills.remove(existingBill.get());
            bills.add(dbObj);
            return new SaveResult(dbObj.id);
        }
        else {
            bills.add(dbObj);
            dbObj.id = findMaxId() + 1;
            return new SaveResult(dbObj.id);
        }
    }

    private long findMaxId() {
        return seq(bills).map(bill -> bill.id).max().orElse(0l);
    }
}
