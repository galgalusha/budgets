package galko.budgets.business.model.tinytypes;

import java.util.Optional;

public class Id {

    private Id(Optional<Long> value) {
        this.value = value;
    }

    private final Optional<Long> value;

    public static Id of(long value) {
        return new Id(Optional.of(value));
    }

    public static Id empty() {
        return new Id(Optional.empty());
    }

    public long getValue() {
        return value.get();
    }

    public boolean isEmpty() {
        return !value.isPresent();
    }
}
