package org.stx;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ServiceRegistration {
    private final Map<Class<?>, Object> services;

    public ServiceRegistration() {
        services = new HashMap<>();
    }

    public <T> T getService(Class<T> type) {
        return type.cast(services.get(type));
    }

    public <T> void registrationService(Class<T> type, T t) {
        services.put(type, t);
    }

    public <T> void registrationService(Class<T> type, Class<? extends T> tImpl) {
        try {
            services.put(type, tImpl.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> void registrationService(Class<T> type, String name) {
        try {
            Class<?> iClass = Class.forName(name);
            services.put(type, type.cast(iClass.getDeclaredConstructor().newInstance()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> T unregisterService(Class<T> type) {
        return type.cast(services.remove(type));
    }

    public Map<Class<?>, Object> getServices() {
        return services;
    }
}
