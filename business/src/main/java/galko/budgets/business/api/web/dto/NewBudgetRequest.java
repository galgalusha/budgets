package galko.budgets.business.api.web.dto;

public class NewBudgetRequest extends Request {

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    private String name;
    private int amount;


    public NewBudgetRequest() {
    }
}
