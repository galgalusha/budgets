package galko.budgets.business.api.web.dto;

public class Budget {

    private long id;
    private String name;
    private int amount;

    public static class Builder {
        private Budget budget = new Budget();

        public Builder withId(long value) {
            budget.id = value;
            return this;
        }

        public Builder withAmount(int value) {
            budget.amount = value;
            return this;
        }

        public Builder withName(String value) {
            budget.name = value;
            return this;
        }

        public Budget get() {
            return budget;
        }

    }
}
