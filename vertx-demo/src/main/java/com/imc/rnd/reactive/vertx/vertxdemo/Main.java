package com.imc.rnd.reactive.vertx.vertxdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Vertx;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        log.debug("verticles deployment started");

        var vertx = Vertx.vertx();
        vertx.deployVerticle(new SupervisorVerticle());

        log.debug("exiting...");
    }
}