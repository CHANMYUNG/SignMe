package com.nooheat.core;

import io.vertx.core.Vertx;
/**
 * Created by NooHeat on 16/06/2017.
 */

public class Main {
    public static void main(String[] args){
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new CoreVirticle());
    }
}
