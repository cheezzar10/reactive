package com.imc.rnd.reactive.vertx.vertxdemo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

public class ConsoleVerticle extends AbstractVerticle {
    private static final Logger log = LoggerFactory.getLogger(ConsoleVerticle.class);
    
    @Override
    public void start(Promise<Void> startPromise) {
        log.debug("starting console");

        vertx.setPeriodic(1000, timerId -> {
            var keyPress = new JsonObject(Map.of("key", "a"));
            vertx.eventBus().send("console.output", keyPress);
        });

        log.debug("console started");
        startPromise.complete();
    }
}
