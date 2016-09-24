package galko.budgets.service_layer.dto;

public class Budget {

    private long id;
    private String name;
    private int allocation;
    private int used;

    public Budget() {
    }

    public static Builder config() {
        return new Builder();
    }

    public static class Builder {

        private final Budget result = new Budget();

        public Builder withId(Long value) {
            result.id = value;
            return this;
        }

        public Builder withName(String value) {
            result.name = value;
            return this;
        }

        public Builder withAllocation(int value) {
            result.allocation = value;
            return this;
        }

        public Builder withUsed(int value) {
            result.used = value;
            return this;
        }

        public Budget create() {
            return result;
        }
    }
}
