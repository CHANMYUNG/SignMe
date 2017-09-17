package com.nooheat.controller.task;

import com.nooheat.manager.RequestManager;
import com.nooheat.model.Task;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;
import org.json.JSONArray;

/**
 * Created by NooHeat on 24/07/2017.
 */
@API(category = Category.TASK, summary = "일정 목록 조회", response = "[{‘endDate’:’2017-08-11 00:00:00', 'writerUid':'2', 'title':'가정통신문23', 'tid':'5', 'writerName':'정근철', 'startDate':'2017-08-08 00:00:00'}]", successCode = 200, failureCode = 400)
@URIMapping(uri = "/task", method = HttpMethod.GET)
public class GetTaskList implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext context) {
        HttpServerRequest req = context.request();
        JSONArray response = null;
        try {
            if (RequestManager.paramValidationCheck(req.getParam("year"))) {
                int year = Integer.parseInt(req.getParam("year"));
                if (RequestManager.paramValidationCheck(req.getParam("month"))) {

                    int month = Integer.parseInt(req.getParam("month"));

                    response = Task.findByYearAndMonth(year, month);
                } else response = Task.findByYear(year);
            } else {
                response = Task.findAll();
            }

        } catch (NumberFormatException exception) {
        }

        if (response != null) context.response().setStatusCode(200).end(response.toString());
        else context.response().setStatusCode(200).end(new JSONArray().toString());
    }
}
