package com.nooheat.controller.task;

import com.nooheat.manager.JWT;
import com.nooheat.manager.RequestManager;
import com.nooheat.model.Task;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.DateTime;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by NooHeat on 25/07/2017.
 */
@API(category = Category.TASK, summary = "일정 추가", successCode = 201, failureCode = 400, etc = "비로그인 : 401, 잘못된 요청 : 400, 권한 없음 : 403")
@URIMapping(uri = "/task", method = HttpMethod.POST)
public class PostTask implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext context) {
        HttpServerRequest req = context.request();
        HttpServerResponse res = context.response();
        JWT token = JWT.verify(context);

        if (token == null) {
            res.setStatusCode(401).end();
            return;
        }

        if (!token.isAdmin()) {
            res.setStatusCode(403).end();
            return;
        }

        String title = context.request().getFormAttribute("title");
        String summary = context.request().getFormAttribute("summary");
        String startDate = DateTime.getDateNow();
        String endDate = context.request().getFormAttribute("endDate");

        if (RequestManager.paramValidationCheck(title, summary, endDate) == false) {
            res.setStatusCode(400).end();
            return;
        }

        Task task = new Task(token.getUid(), title, summary, startDate, endDate, "#" + "AB63DC", null);

        if (task.isDuplicated()) {
            res.setStatusCode(400).end("Duplicated");
            return;
        }

        boolean result = false;
        try {
            result = task.save();
        } catch (SQLException e) {
            res.setStatusCode(500).end();
            return;
        }

        if (result) res.setStatusCode(201).end();
        else res.setStatusCode(400).end();
    }

}
