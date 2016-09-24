package galko.budgets.service_layer.dto;

public class NewExpenseRequest {

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
