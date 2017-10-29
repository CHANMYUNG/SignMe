package com.nooheat.core;

import com.nooheat.manager.Mail;
import com.nooheat.secure.AES256;
import com.nooheat.secure.SHA256;
import com.nooheat.support.FCM;
import com.nooheat.support.Routing;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.*;
import io.vertx.ext.web.impl.RouterImpl;
import io.vertx.ext.web.sstore.LocalSessionStore;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by NooHeat on 16/06/2017.
 */
public class CoreVirticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        int serverPort = 7800;

        FCM.notification("fq-V3XirFhE:APA91bExeIOWzpFME13Nq1_zUGAf9AQL7THQk0BqK9BEy_bOkcKywnJuj-Rq1tHLJD4wltC5pMvsROotQFrnF28sGmPXaom934zhDh1jztVkBwznWoB20IG5kkeUg-OSqs7DHQyTZ-OJ", "asdas");
        System.out.println(AES256.encrypt("test"));
        System.out.println(SHA256.encrypt("1234"));
        router.route().handler(BodyHandler.create().setUploadsDirectory("upload-files"));
        router.route().handler(CookieHandler.create());
        router.route().handler(CorsHandler.create("*"));

        Routing.route(router, "com.nooheat.controller");
        router.route().handler(StaticHandler.create());

        vertx.createHttpServer().requestHandler(router::accept).listen(serverPort);

    }
}
