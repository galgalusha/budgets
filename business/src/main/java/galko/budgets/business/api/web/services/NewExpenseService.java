package galko.budgets.business.api.web.services;

import galko.budgets.business.api.web.IService;
import galko.budgets.business.api.web.dto.NewExpenseRequest;
import galko.budgets.business.api.web.dto.TextMessage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class NewExpenseService implements IService<NewExpenseRequest, TextMessage> {

    private final static Logger logger = LogManager.getLogger(NewExpenseService.class);

    public TextMessage handle(NewExpenseRequest request) {
        logger.info("----------- request --------------");
        logger.info("budget id: " + request.getBudgetId());
        logger.info("amount: " + request.getAmount());

        return new TextMessage("Saved Successfully");
    }

}
