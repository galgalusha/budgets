package galko.budgets.business.api.web.services;

import galko.budgets.business.api.web.IService;
import galko.budgets.business.api.web.dto.Budget;
import galko.budgets.business.api.web.dto.GetBudgetRequest;
import galko.service_locator.ServiceLocator;

public class GetBudgetService implements IService<GetBudgetRequest, Budget> {

    public GetBudgetService(ServiceLocator serviceLocator) {
    }

    public Budget handle(GetBudgetRequest request) {
        return new Budget.Builder()
                .withName("Groceries")
                .withId(1l)
                .withAmount(2000)
                .get();
    }

}
