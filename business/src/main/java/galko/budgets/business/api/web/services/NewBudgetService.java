package galko.budgets.business.api.web.services;

import galko.budgets.business.api.os.ITimeService;
import galko.budgets.business.api.web.IService;
import galko.budgets.business.api.web.dto.NewBudgetRequest;
import galko.budgets.business.api.web.dto.NewExpenseRequest;
import galko.budgets.business.api.web.dto.TextMessage;
import galko.budgets.business.model.Budget;
import galko.budgets.business.model.Monthly;
import galko.budgets.business.model.tinytypes.*;
import galko.budgets.persistency.api.dto.BudgetDbo;
import galko.budgets.persistency.api.query.IBudgetDba;
import galko.service_locator.ServiceLocator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class NewBudgetService implements IService<NewBudgetRequest, TextMessage> {

    private final static Logger logger = LogManager.getLogger(NewBudgetService.class);

    private final IBudgetDba budgetDba;
    private final ServiceLocator serviceLocator;

    public NewBudgetService(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
        budgetDba = serviceLocator.resolve(IBudgetDba.class);
    }

    public TextMessage handle(NewBudgetRequest request) {

        final Budget budget = new Budget(
                serviceLocator,
                Id.empty(),
                UserId.of(request.getUserId().get()),
                Name.of(request.getName()),
                BudgetAmount.of(request.getAmount()),
                new Monthly(serviceLocator.resolve(ITimeService.class)));

        final Budget resultBudget = budget.save();

        logger.info("Created new budget. Name: " + resultBudget.name.value +
                ", Amount: " + resultBudget.amount.value +
                ", Id: " + resultBudget.id.getValue() +
                ", User: " + resultBudget.userId.value);

        return new TextMessage("Saved Successfully");
    }

}
