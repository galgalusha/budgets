package galko.budgets.business.model;

import galko.budgets.business.api.os.ITimeService;
import galko.budgets.business.model.tinytypes.*;
import galko.budgets.persistency.api.InsertResult;
import galko.budgets.persistency.api.dto.BillDbo;
import galko.budgets.persistency.api.dto.BudgetDbo;
import galko.budgets.persistency.api.query.IBillDba;
import galko.budgets.persistency.api.query.IBudgetDba;
import galko.service_locator.ServiceLocator;
import java.util.Collection;
import java.util.Optional;

public class Budget {

    private final ServiceLocator serviceLocator;
    private final ITimeService timeService;
    private final IBillDba billDba;
    private final IBudgetDba budgetDba;

    public Budget(ServiceLocator serviceLocator, Id id, UserId userId, Name name, BudgetAmount amount, TimePeriod period) {
        this.serviceLocator = serviceLocator;
        this.timeService = serviceLocator.resolve(ITimeService.class);
        this.billDba = serviceLocator.resolve(IBillDba.class);
        this.budgetDba = serviceLocator.resolve(IBudgetDba.class);
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.amount = amount;
        this.period = period;
    }

    public Budget(ServiceLocator serviceLocator, BudgetDbo dbObj) {
        this(serviceLocator,
                Id.of(dbObj.id),
                UserId.of(dbObj.userId),
                Name.of(dbObj.name),
                BudgetAmount.of(dbObj.amount),
                TimePeriod.fromDbo(serviceLocator.resolve(ITimeService.class), dbObj.period));
    }

    public BudgetDbo toDbo() {
        BudgetDbo dbo = new BudgetDbo();
        dbo.amount = this.amount.value;
        dbo.name = this.name.value;
        dbo.period = this.period.toDbo();
        dbo.userId = this.userId.value;
        if (!this.id.isEmpty()) {
            dbo.id = this.id.getValue();
        }
        return dbo;
    }

    public final Id id;
    public final UserId userId;
    public final Name name;
    public final BudgetAmount amount;
    public final TimePeriod period;

    public Bill getActiveBill() {
        return findActiveBillInDb().orElseGet(this::createNewBill);
    }

    private static <T> T first(Iterable<T> i) {
        return i.iterator().next();
    }

    private Optional<Bill> findActiveBillInDb() {
        Collection<BillDbo> fromDb = billDba.getBillByBudgetIdWithEndDateGreaterThan(this.id.getValue(), timeService.getCurrent());
        return fromDb.isEmpty()
                ? Optional.<Bill>empty()
                : Optional.of(new Bill(serviceLocator, first(fromDb)));
    }

    private Bill createNewBill() {
        Bill newBill = new Bill(
                serviceLocator,
                Id.empty(),
                this.userId,
                BudgetId.of(this.id.getValue()),
                StartDate.of(this.period.beginningOfcurrent()),
                EndDate.of(this.period.endOfcurrent()),
                BillAmount.of(0));

        BillDbo dbObj = newBill.toDbObject();

        InsertResult insertResult = billDba.insert(newBill.toDbObject());

        dbObj.id = insertResult.id;

        return new Bill(serviceLocator, dbObj);
    }

    public Budget save() {
        if (this.id.isEmpty()) {
            return insertToDb();
        }
        else {
            budgetDba.update(toDbo());
            return this;
        }
    }

    private Budget insertToDb() {
        BudgetDbo dbo = toDbo();
        InsertResult insertResult = budgetDba.insert(dbo);
        dbo.id = insertResult.id;
        return new Budget(serviceLocator, dbo);
    }
}
