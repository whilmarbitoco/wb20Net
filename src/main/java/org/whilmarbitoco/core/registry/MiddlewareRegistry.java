package org.whilmarbitoco.core.registry;

import org.whilmarbitoco.core.exception.HttpException;
import org.whilmarbitoco.core.http.Middleware;
import org.whilmarbitoco.exception.InternalServerException;
import org.whilmarbitoco.exception.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MiddlewareRegistry {

    private final Map<String, Middleware> middlewares = new HashMap<>();
    private final List<Middleware> global = new ArrayList<>();

    public MiddlewareRegistry() {
        global();
        register();
    }

    protected void add(String name, Middleware middleware) {
        if (middlewares.containsKey(name)) {
            throw new RuntimeException(name + " Middleware already registered");
        }

        middlewares.put(name, middleware);
    }

    public Middleware getMiddleware(String name) throws HttpException {
        if (!middlewares.containsKey(name)) {
            throw new InternalServerException(name + " Middleware not registered");
        }
        return middlewares.get(name);
    }

    protected void addGlobal(Middleware middleware) {
        global.add(middleware);
    }

    public abstract void global();

    public abstract void register();

    public final Map<String, Middleware> registeredMiddlewares() {
        return middlewares;
    }

    public final List<Middleware> globalMiddlewares() {
        return global;
    }
}
