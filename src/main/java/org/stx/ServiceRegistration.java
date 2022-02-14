package org.stx;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceRegistration {
    private final static Logger LOGGER = Logger.getLogger(ServiceRegistration.class.getName());

    private final Map<Class<?>, Object> services;

    public ServiceRegistration() {
        services = new HashMap<>();
    }

    public <T> T getService(Class<T> type) {
        Objects.requireNonNull(type, "type is not be null");
        return type.cast(services.get(type));
    }

    public <T> void registrationService(Class<T> type, T t) {
        services.put(type, t);
    }

    public <T> void registrationService(Class<T> type, Class<? extends T> tImpl) {
        try {
            services.put(type, tImpl.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public <T> void registrationService(Class<T> type, String name) {
        try {
            Class<?> iClass = Class.forName(name);
            services.put(type, type.cast(iClass.getDeclaredConstructor().newInstance()));
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public <T> T unregisterService(Class<T> type) {
        return type.cast(services.remove(type));
    }
}
