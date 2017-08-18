package com.nooheat.controller.task;

import com.nooheat.model.Task;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import org.json.JSONArray;

/**
 * Created by NooHeat on 24/07/2017.
 */
@API(category = Category.TASK, summary = "일정 목록 조회", successCode = 200, failureCode = 400)
@URIMapping(uri = "/task", method = HttpMethod.GET)
public class GetTaskList implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext context) {
        JSONArray response = Task.findAll();

        if (response != null) context.response().setStatusCode(200).end(response.toString());

        else context.response().setStatusCode(400).end();
    }
}
