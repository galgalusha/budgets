package galko.budgets;

import galko.budgets.business.api.db.dto.BillDbo;
import galko.budgets.business.api.db.query.IBillDba;
import galko.budgets.business.model.tinytypes.EndDate;
import galko.budgets.business.model.tinytypes.UserId;
import java.util.Collection;
import java.util.LinkedHashSet;
import static org.jooq.lambda.Seq.seq;

public class MemoryBillDba implements IBillDba {

    public Collection<BillDbo> bills = new LinkedHashSet<>();

    @Override
    public Collection<BillDbo> getBillsWithEndDateGreaterThan(UserId id, EndDate minEndDate) {
        return seq(bills)
                .filter(x -> x.userId.value.equals(id.value))
                .filter(x -> x.endDate.value.after(minEndDate.value))
                .toList();
    }
}
