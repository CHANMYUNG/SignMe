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

@API(category = Category.NOTIFICATION, summary = "푸시알림 온오프 설정", requestBody = "set : boolean", successCode = 200, failureCode = 500)
@URIMapping(uri = "/notification/switch", method = HttpMethod.PUT)
public class NotificationSwitch implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();

        JWT token = JWT.verify(ctx);
        String uid = token.getUid();
        boolean notificationEnabled = Boolean.parseBoolean(req.getFormAttribute("set"));

        try {
            DBManager.update("UPDATE " + (token.isAdmin() ? "ADMIN" : "USER") + " SET notificationEnabled = ? WHERE uid = ?", notificationEnabled, uid);
            DBManager.commit();
            res.setStatusCode(200).end();
        } catch (SQLException e) {
            e.printStackTrace();
            DBManager.rollback();
            res.setStatusCode(500).end();
        }
    }
}
