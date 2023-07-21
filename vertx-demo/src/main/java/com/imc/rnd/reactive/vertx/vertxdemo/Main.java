package com.imc.rnd.reactive.vertx.vertxdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Future;
import io.vertx.core.Vertx;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws Exception {
        Thread.currentThread().setName("main");

        log.debug("verticles deployment started");

        var vertx = Vertx.vertx();
        Future<String> deploymentResult = vertx.deployVerticle(new SupervisorVerticle());

        deploymentResult.onSuccess(supervisorId -> {
            log.debug("supervisor started - supervisor id: {}", supervisorId);
        });

        deploymentResult.onFailure(deploymentEx -> {
            log.error("supervisor start failed: ", deploymentEx);
        });

        log.debug("exiting...");
    }
}