package com.nooheat.controller.task;

import com.nooheat.manager.JWT;
import com.nooheat.manager.RequestManager;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by NooHeat on 25/07/2017.
 */
@API(category = Category.TASK, summary = "일정 추가", successCode = 201, failureCode = 400)
@URIMapping(uri="/task", method = HttpMethod.POST)
public class PostTask implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext context) {
        JWT token = JWT.verify(context);
        if(token == null){
            context.response().setStatusCode(401).end();
            return;
        }
        if(!token.isAdmin()){
            context.response().setStatusCode(403).end();
            return;
        }

        String title = context.request().getFormAttribute("name");
        String summary = context.request().getFormAttribute("summary");
        String startDate = context.request().getFormAttribute("startDate");
        String endDate = context.request().getFormAttribute("endDate");

        if(RequestManager.paramValidationCheck(title, summary, startDate, endDate)){
            context.response().setStatusCode(400).end();
            return;
        }

        post(title, summary, startDate, endDate);
    }

    public static void post(String title, String summary, String startDate, String endDate){

    }
}
