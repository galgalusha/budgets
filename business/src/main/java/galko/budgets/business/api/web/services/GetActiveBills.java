package galko.budgets.business.api.web.services;

import galko.budgets.business.api.web.IService;
import galko.budgets.business.api.web.dto.ActiveBill;
import galko.budgets.business.api.web.dto.EmptyRequest;
import galko.budgets.business.model.User;
import galko.budgets.business.model.tinytypes.UserId;
import galko.service_locator.ServiceLocator;
import java.util.List;
import static org.jooq.lambda.Seq.seq;

public class GetActiveBills implements IService<EmptyRequest, List<ActiveBill>> {

    private final ServiceLocator serviceLocator;

    public GetActiveBills(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    public List<ActiveBill> handle(EmptyRequest request) {

        User user = new User(serviceLocator, UserId.of(request.getUserId().get()));

        return seq(user.getActiveBills())
                .innerJoin(user.getBudgets(), (bill, budget) -> bill.budgetId.value == budget.id.value)
                .map(pair -> ActiveBill.config()
                                .withBillAmount(pair.v1().billAmount.value)
                                .withBudgetAmount(pair.v2().amount.value)
                                .withBudgetId(pair.v2().id.value)
                                .withBudgetName(pair.v2().name.value)
                                .create())
                .toList();
    }
}
