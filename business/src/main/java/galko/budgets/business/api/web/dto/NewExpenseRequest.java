package galko.budgets.business.api.web.dto;

public class NewExpenseRequest extends Request {

    public int getBudgetId() {
        return budgetId;
    }

    public int getAmount() {
        return amount;
    }

    private int budgetId;
    private int amount;


    public NewExpenseRequest() {
    }
}
