package galko.budgets;

import galko.budgets.persistency.api.dto.BillDbo;
import galko.budgets.persistency.api.query.IBillDba;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
}
