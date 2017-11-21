package com.nooheat.core;

import com.mysql.cj.api.mysqla.result.Resultset;
import com.nooheat.database.DBManager;
import com.nooheat.manager.Mail;
import com.nooheat.secure.AES256;
import com.nooheat.secure.SHA256;
import com.nooheat.support.DateTime;
import com.nooheat.support.FCMPush;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

//TODO : notification 리프레시토큰/enables

/**
 * Created by NooHeat on 16/06/2017.
 */

public class CoreVirticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        int serverPort = 7800;

        Thread notificationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String after1HourTime = DateTime.getAfter1Hour();
                String now = DateTime.getDateNow();
                System.out.println(after1HourTime);
                System.out.println(now);
                try {
                    ResultSet rs = DBManager.execute("SELECT * FROM responseLetter WHERE closeDate BETWEEN ? AND ?", now, after1HourTime);
                    while (rs.next()) {
                        String title = rs.getString("title");
                        ResultSet inner_rs = DBManager.execute("SELECT token FROM USER AS U WHERE notificationEnabled = true AND (SELECT count(uid) FROM letterAnswer WHERE uid = U.uid AND letterNumber = ?) < 1 AND token IS NOT NULL", rs.getInt("letterNumber"));
                        while (inner_rs.next())
                            FCMPush.pushFCMNotification(inner_rs.getString("token"), "[응답형] " + title + "의 마감이 얼마남지 않았습니다");
                    }

                    rs = DBManager.execute("SELECT * FROM survey WHERE closeDate BETWEEN ? AND ?", now, after1HourTime);
                    while (rs.next()) {
                        String title = rs.getString("title");
                        ResultSet inner_rs = DBManager.execute("SELECT token FROM USER AS U WHERE notificationEnabled = true AND (SELECT count(distinct uid) FROM surveyAnswer WHERE uid = U.uid AND letterNumber = ?) < 1 AND token IS NOT NULL", rs.getInt("letterNumber"));
                        while (inner_rs.next())
                            FCMPush.pushFCMNotification(inner_rs.getString("token"), "[설문조사] " + title + "의 마감이 얼마남지 않았습니다");
                    }

                    Thread.sleep(60 * 60 * 1000);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        notificationThread.start();
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
