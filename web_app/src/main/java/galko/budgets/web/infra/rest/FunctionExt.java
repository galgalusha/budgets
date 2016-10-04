package galko.budgets.web.infra.rest;

import org.jooq.lambda.function.Consumer2;
import java.util.function.Function;

public interface FunctionExt<T, V> extends Function<T, V> {

    default Function<T, V> andThen(Consumer2<T, V> consumer) {
        return input -> {
            V output = apply(input);
            consumer.accept(input, output);
            return output;
        };
    }
}