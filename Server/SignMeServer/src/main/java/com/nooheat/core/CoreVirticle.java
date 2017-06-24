package com.nooheat.core;

import com.github.aesteve.vertx.nubes.NubesServer;
import com.github.aesteve.vertx.nubes.VertxNubes;
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
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.impl.RouterImpl;
import io.vertx.ext.web.sstore.LocalSessionStore;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Logger;

import static com.github.aesteve.vertx.nubes.utils.async.AsyncUtils.completeOrFail;
import static com.github.aesteve.vertx.nubes.utils.async.AsyncUtils.ignoreResult;
import static com.github.aesteve.vertx.nubes.utils.async.AsyncUtils.onSuccessOnly;

/**
 * Created by NooHeat on 16/06/2017.
 */
public class CoreVirticle extends AbstractVerticle {

    private static final io.vertx.core.logging.Logger LOG = LoggerFactory.getLogger(NubesServer.class);

    protected HttpServer server;
    protected HttpServerOptions options;
    protected VertxNubes nubes;

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);
        JsonObject config = context.config();
        config.put("src-package", "com.nooheat");
        options = new HttpServerOptions();
        options.setHost(config.getString("host", "localhost"));
        options.setPort(config.getInteger("port", 8080));
        nubes = new VertxNubes(vertx, config);
    }

    @Override
    public void start(Future<Void> future) {
//        Router router = Router.router(vertx);
//        router.route().handler(BodyHandler.create().setUploadsDirectory("files"));
//        router.route().handler(CookieHandler.create());
//        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
//        router.route().handler(StaticHandler.create());
        server = vertx.createHttpServer(options);
        nubes.bootstrap(onSuccessOnly(future, _router -> {
            _router.route().handler(BodyHandler.create().setUploadsDirectory("files"));
            _router.route().handler(CookieHandler.create());
            _router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
            _router.route().handler(StaticHandler.create());
            server.requestHandler(_router::accept);
            server.listen(ignoreResult(future));
            LOG.info("Server listening on port : " + options.getPort());
        }));
    }

    @Override
    public void stop(Future<Void> future) {
        nubes.stop(nubesRes -> closeServer(future));
    }

    private void closeServer(Future<Void> future) {
        if (server != null) {
            LOG.info("Closing HTTP server");
            server.close(completeOrFail(future));
        } else {
            future.complete();
        }
    }
}
