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
                log.debug("console started - console id: {}", consoleVerticleId);

                vertx.deployVerticle(new TerminalVerticle(), terminalDeploymentResult -> {
                    if (terminalDeploymentResult.succeeded()) {
                        var terminalVerticleId = terminalDeploymentResult.result();
                        log.debug("terminal started - terminal id: {}", terminalVerticleId);

                        log.debug("all devices started");

                        var systemId = deploymentID();
                        vertx.setTimer(5000, tid -> {
                            vertx.undeploy(systemId);
                        });

                        startPromise.complete();
                    } else {
                        log.error("terminal start failed: ", terminalDeploymentResult.cause());
                        startPromise.fail(terminalDeploymentResult.cause());
                    }
                });
            } else {
                log.error("console start failed: ", consoleDeploymentResult.cause());
                startPromise.fail(consoleDeploymentResult.cause());
            }
        });
    }

    @Override
    public void stop(Promise<Void> stopPromise) {
        log.debug("system stopped");
        stopPromise.complete(null);

        log.debug("closing Vert.x");
        vertx.close();
    }
}
