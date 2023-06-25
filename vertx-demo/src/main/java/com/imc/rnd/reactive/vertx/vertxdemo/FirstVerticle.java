package com.imc.rnd.reactive.vertx.vertxdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class FirstVerticle extends AbstractVerticle {
    private final Logger log = LoggerFactory.getLogger(FirstVerticle.class);

    public void start(Promise<Void> startPromise) {
        log.debug("verticle initialization started");
        startPromise.complete();
        log.debug("verticle initialization completed");
    }
}
