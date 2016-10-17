package galko.budgets.business.api.web.services;

import galko.budgets.business.api.web.IService;
import galko.budgets.business.api.web.dto.Budget;
import galko.budgets.business.api.web.dto.GetBudgetRequest;
import galko.budgets.persistency.api.dto.BudgetDbo;
import galko.budgets.persistency.api.query.IBudgetDba;
import galko.service_locator.ServiceLocator;
import java.util.Optional;

public class GetBudgetService implements IService<GetBudgetRequest, Budget> {

    private final ServiceLocator serviceLocator;

    public GetBudgetService(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    public Budget handle(GetBudgetRequest request) {

        Optional<BudgetDbo> dbResult = serviceLocator
                .resolve(IBudgetDba.class)
                .getById(request.getBudgetId());

        if (!dbResult.isPresent()) {
            throw new RuntimeException("could not find budget in DB with id=" + request.getBudgetId());
        }

        return new Budget.Builder()
                .withAmount(dbResult.get().amount)
                .withId(dbResult.get().id)
                .withName(dbResult.get().name)
                .get();
    }
}
