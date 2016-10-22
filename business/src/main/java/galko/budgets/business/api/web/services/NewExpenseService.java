package galko.budgets.business.api.web.services;

import galko.budgets.business.api.web.IService;
import galko.budgets.business.api.web.dto.NewExpenseRequest;
import galko.budgets.business.api.web.dto.TextMessage;
import galko.budgets.business.model.Budget;
import galko.budgets.business.model.tinytypes.BillAmount;
import galko.budgets.persistency.api.dto.BudgetDbo;
import galko.budgets.persistency.api.query.IBudgetDba;
import galko.service_locator.ServiceLocator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class NewExpenseService implements IService<NewExpenseRequest, TextMessage> {

    private final static Logger logger = LogManager.getLogger(NewExpenseService.class);

    private final IBudgetDba budgetDba;
    private final ServiceLocator serviceLocator;

    public NewExpenseService(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
        budgetDba = serviceLocator.resolve(IBudgetDba.class);
    }

    public TextMessage handle(NewExpenseRequest request) {

        final BudgetDbo budgetDbo = budgetDba.getById(request.getBudgetId()).get();

        final Budget budget = new Budget(serviceLocator, budgetDbo);

        budget.getActiveBill().addExpense(BillAmount.of(request.getAmount()));

        logger.info("Added expense of " + request.getAmount() + " to budget " + request.getBudgetId() + " of user " + request.getUserId().get());

        return new TextMessage("Saved Successfully");
    }

}
