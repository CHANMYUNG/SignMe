package com.nooheat.controller.task;

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

/**
 * Created by NooHeat on 18/08/2017.
 */
@API(category = Category.TASK, summary = "일정 내용 보기", successCode = 200, failureCode = 400, etc = "잘못된 요청 : 400")
@URIMapping(uri = "/task/:tid", method = HttpMethod.GET)
public class GetTask implements Handler<RoutingContext> {


    @Override
    public void handle(RoutingContext ctx) {

        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();

        int tid = -1;

        try {
            tid = Integer.parseInt(req.getParam("tid"));
        } catch (NumberFormatException e) {
            res.setStatusCode(400).end();
            return;
        }

        Task task = Task.findOne(tid);

        if (task != null) res.setStatusCode(200).end(task.toString());

        else res.setStatusCode(400).end();
    }

}
