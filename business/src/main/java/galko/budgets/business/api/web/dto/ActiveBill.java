package galko.budgets.business.api.web.dto;

public class ActiveBill {

    private long budgetId;
    private String budgetName;
    private int budgetAmount;
    private int billAmount;

    public ActiveBill() {
    }

    public static Builder config() {
        return new Builder();
    }

    public static class Builder {

        private final ActiveBill result = new ActiveBill();

        public Builder withBudgetId(Long value) {
            result.budgetId = value;
            return this;
        }

        public Builder withBudgetName(String value) {
            result.budgetName = value;
            return this;
        }

        public Builder withBudgetAmount(int value) {
            result.budgetAmount = value;
            return this;
        }

        public Builder withBillAmount(int value) {
            result.billAmount = value;
            return this;
        }

        public ActiveBill create() {
            return result;
        }
    }
}
