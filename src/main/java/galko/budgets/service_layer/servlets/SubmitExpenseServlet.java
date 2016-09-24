package galko.budgets.service_layer.servlets;

import galko.budgets.service_layer.dto.NewExpenseRequest;
import galko.budgets.service_layer.dto.TextMessage;
import galko.budgets.service_layer.infra.rest.JsonPostServlet;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class SubmitExpenseServlet extends JsonPostServlet<NewExpenseRequest, TextMessage> {

    private final static Logger logger = LogManager.getLogger(SubmitExpenseServlet.class);

    @Override
    protected Class<NewExpenseRequest> getRequestType() {
        return NewExpenseRequest.class;
    }

    @Override
    protected TextMessage doJson(NewExpenseRequest request) {

        logger.info("----------- request --------------");
        logger.info("budget id: " + request.getBudgetId());
        logger.info("amount: " + request.getAmount());

        return new TextMessage("Saved Successfully");
    }
}
