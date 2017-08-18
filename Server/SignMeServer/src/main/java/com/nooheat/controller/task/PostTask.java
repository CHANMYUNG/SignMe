package com.nooheat.controller.task;

import com.nooheat.manager.JWT;
import com.nooheat.manager.RequestManager;
import com.nooheat.model.Task;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by NooHeat on 25/07/2017.
 */
@API(category = Category.TASK, summary = "일정 추가", successCode = 201, failureCode = 400, etc = "비로그인 : 401, 잘못된 요청 : 400, 권한 없음 : 403")
@URIMapping(uri = "/task", method = HttpMethod.POST)
public class PostTask implements Handler<RoutingContext> {

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

        String title = context.request().getFormAttribute("title");
        String summary = context.request().getFormAttribute("summary");
        String startDate = context.request().getFormAttribute("startDate");
        String endDate = context.request().getFormAttribute("endDate");

        if (RequestManager.paramValidationCheck(title, summary, startDate, endDate) == false) {
            context.response().setStatusCode(400).end();
            return;
        }

        Task task = new Task(token.getUid(), title, summary, startDate, endDate);

        if (task.isDuplicated()) {
            context.response().setStatusCode(400).end("Duplicated");
            return;
        }

        boolean result = task.save();

        if (result) context.response().setStatusCode(201).end();
        else context.response().setStatusCode(400).end();
    }

}
