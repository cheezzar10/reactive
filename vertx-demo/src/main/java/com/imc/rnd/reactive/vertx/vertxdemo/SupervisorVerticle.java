package com.imc.rnd.reactive.vertx.vertxdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class SupervisorVerticle extends AbstractVerticle {
    private final Logger log = LoggerFactory.getLogger(SupervisorVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) {
        log.debug("system initialization started");

        vertx.deployVerticle(new ConsoleVerticle(), consoleDeploymentResult -> {
            if (consoleDeploymentResult.succeeded()) {
                var consoleVerticleId = consoleDeploymentResult.result();
                log.debug("console verticle deployment completed - deployment id: {}", consoleVerticleId);

                vertx.deployVerticle(new TerminalVerticle(), terminalDeploymentResult -> {
                    if (terminalDeploymentResult.succeeded()) {
                        var terminalVerticleId = terminalDeploymentResult.result();
                        log.debug("terminal verticle deployment completed - deployment id: {}", terminalVerticleId);

                        log.debug("system initialization completed");
                        startPromise.complete();
                    } else {
                        log.error("terminal verticle deployment failed: ", terminalDeploymentResult.cause());
                        startPromise.fail(terminalDeploymentResult.cause());
                    }
                });
            } else {
                log.error("console verticle deployment failed: ", consoleDeploymentResult.cause());
                startPromise.fail(consoleDeploymentResult.cause());
            }
        });
    }
}
