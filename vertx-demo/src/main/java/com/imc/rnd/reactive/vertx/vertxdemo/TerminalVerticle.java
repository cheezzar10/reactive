package com.imc.rnd.reactive.vertx.vertxdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class TerminalVerticle extends AbstractVerticle {
    private final Logger log = LoggerFactory.getLogger(TerminalVerticle.class);
    
    @Override
    public void start(Promise<Void> startPromise) {
        log.debug("starting terminal");

        vertx.eventBus().consumer("console.output", (Message<JsonObject> message) -> {
            var messageBody = message.body();
            log.info("key pressed: {}", messageBody.getString("key"));
        });

        log.debug("terminal started");
        startPromise.complete();
    }
}
