package com.nooheat.controller.task;

import com.nooheat.manager.JWT;
import com.nooheat.manager.RequestManager;
import com.nooheat.model.Task;
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
 * Created by NooHeat on 18/08/2017.
 */

@API(category = Category.TASK, summary = "일정 삭제", successCode = 200, failureCode = 400, etc = "잘못된 요청 : 400, 비로그인 : 401")
@URIMapping(uri = "/task/:tid", method = HttpMethod.DELETE)
public class DelTask implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext context) {
        JWT token = JWT.verify(context);

        if (token == null) {
            context.response().setStatusCode(401).end();
            return;
        }

        if (!token.isAdmin()) {
            context.response().setStatusCode(403).end();
            return;
        }

        HttpServerResponse res = context.response();
        HttpServerRequest req = context.request();

        int tid = -1;

        try {
            tid = Integer.parseInt(req.getParam("tid"));
        } catch (NumberFormatException e) {
            res.setStatusCode(400).end();
            return;
        }

        Task task = Task.findOne(tid);


        try {
            if (task.delete()) res.setStatusCode(200).end();

            else res.setStatusCode(400).end();
        } catch (SQLException e) {
            e.printStackTrace();
            res.setStatusCode(500).end();
        }


    }
}
