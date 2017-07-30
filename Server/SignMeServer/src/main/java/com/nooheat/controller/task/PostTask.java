package com.nooheat.controller.task;

import com.nooheat.manager.UserManager;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import io.netty.util.Recycler;
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
        String title = context.request().getFormAttribute("name");
        String summary = context.request().getFormAttribute("summary");
        String startDate = context.request().getFormAttribute("startDate");
        String endDate = context.request().getFormAttribute("endDate");


        // 세션 값이 존재하지 않는다면 (로그인되지 않은 상태)
        if(context.session().get("sessionKey") == null){
            context.response().setStatusCode(401).end();
        }
        // 세션 값이 존재한다면 (로그인된 상태)
        else{
            String sessionKey = context.session().get("sessionKey");
            System.out.println(sessionKey);
            context.response().end(sessionKey);
            /*
            *  세션 값 가져오는데까지 성공.
            *
            */

        }

    }

    //public void post(String title, String summary, )
}
