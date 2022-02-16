package org.stx;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceRegistration {

    private final static Logger LOGGER = Logger.getLogger(ServiceRegistration.class.getName());

    private static volatile ServiceRegistration INSTANCE = new ServiceRegistration();

    private final Map<Class<?>, Object> services;

    private ServiceRegistration() {
        services = new ConcurrentHashMap<>();
    }

    public static ServiceRegistration getInstance() {
        ServiceRegistration instance = INSTANCE;
        if (instance == null) synchronized (ServiceRegistration.class) {
            instance = INSTANCE;
            if (instance == null) instance = INSTANCE = new ServiceRegistration();
        }
        return instance;
    }

    public <T> T getService(Class<T> type) {
        Objects.requireNonNull(type, "type is not be null");
        return type.cast(services.get(type));
    }

    public <T> void registrationService(Class<T> type, T t) {
        try {
            Objects.requireNonNull(t);
            services.put(type, t);
            LOGGER.info("Successfully registered service: " + type.getName());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public <T> void registrationService(Class<T> type, Class<? extends T> tImpl) {
        try {
            services.put(type, tImpl.getDeclaredConstructor().newInstance());
            LOGGER.info("Successfully registered service: " + type.getName());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public <T> void registrationService(Class<T> type, String name) {
        try {
            Class<?> iClass = Class.forName(name);
            services.put(type, type.cast(iClass.getDeclaredConstructor().newInstance()));
            LOGGER.info("Successfully registered service: " + type.getName());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public <T> void unregisterService(Class<T> type) {
        try {
            services.remove(type);
            LOGGER.info("Successfully unregister service: " + type);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }
}
