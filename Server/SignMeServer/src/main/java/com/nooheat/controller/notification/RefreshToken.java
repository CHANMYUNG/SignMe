package com.nooheat.controller.notification;

import com.nooheat.database.DBManager;
import com.nooheat.manager.JWT;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

import java.sql.SQLException;

/**
 * Created by NooHeat on 06/11/2017.
 */

@API(category = Category.NOTIFICATION, summary = "FCM 토큰 리프레시", requestBody = "token : String", successCode = 200, failureCode = 500, etc = "[가정통신문은 type으로 구분.], 비로그인 : 401, 없는 letterNumber : 400, 서버 오류 : 500")
@URIMapping(uri = "/notification/token", method = HttpMethod.PUT)
public class RefreshToken implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();

        JWT token = JWT.verify(ctx);
        String uid = token.getUid();
        String FCMToken = req.getFormAttribute("token");

        try {
            DBManager.update("UPDATE " + (token.isAdmin() ? "ADMIN" : "USER") + " SET token = ? WHERE uid = ?", FCMToken, uid);
            DBManager.commit();
            res.setStatusCode(200).end();
        } catch (SQLException e) {
            e.printStackTrace();
            DBManager.rollback();
            res.setStatusCode(500).end();
        }
    }
}
