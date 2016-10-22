package galko.service_locator;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

    private final Map<String, Object> registry = new HashMap<>();

    public void register(Class clazz, Object impl) {
        registry.put(clazz.getCanonicalName(), impl);
    }

    public <T> T resolve(Class<T> clazz) {
        return (T) registry.get(clazz.getCanonicalName());
    }
}
