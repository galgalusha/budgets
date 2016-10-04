package galko.budgets.business.api.web.dto;

public class GetBudgetRequest extends Request {

    public GetBudgetRequest() {
    }

    public GetBudgetRequest(int budgetId) {
        this.budgetId = budgetId;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    private int budgetId;
}
