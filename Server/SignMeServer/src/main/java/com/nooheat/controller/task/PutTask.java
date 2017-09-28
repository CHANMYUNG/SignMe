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

/**
 * Created by NooHeat on 18/08/2017.
 */
@API(category = Category.TASK, summary = "일정 수정", successCode = 200, failureCode = 400)
@URIMapping(uri = "/task/:tid", method = HttpMethod.PUT)
public class PutTask implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();
        JWT token = JWT.verify(ctx);
        if (token == null) {
            res.setStatusCode(401).end();
            return;
        }

        if (token.isAdmin() == false) {
            res.setStatusCode(403).end();
            return;
        }

        int tid = -1;

        try {
            tid = Integer.parseInt(req.getParam("tid"));

            Task task = Task.findOne(tid);

            if (task == null) {
                res.setStatusCode(400).end();
                return;
            }
            System.out.println(task.getWriterUid() == null);
            System.out.println(token.getUid() == null);
            if (task.getWriterUid().equals(token.getUid()) == false) {
                res.setStatusCode(403).end();
                return;
            }

            String title = req.getFormAttribute("title");
            String summary = req.getFormAttribute("summary");
            String startDate = DateTime.getDateNow();
            String endDate = req.getFormAttribute("endDate");

            if (RequestManager.paramValidationCheck(title, summary, startDate, endDate) == false) {
                res.setStatusCode(400).end();
            }
            // 여까지함 씨발
            task.update(title, summary, startDate, endDate);

            if (task.isDuplicated()) {
                res.setStatusCode(400).end("Duplicated");
                return;
            }

            boolean result = task.saveUpdated();

            if (result) res.setStatusCode(200).end();
            else res.setStatusCode(400).end();
            
        } catch (NumberFormatException e) {
            res.setStatusCode(400).end();
        } catch (SQLException ex) {
            ex.printStackTrace();
            res.setStatusCode(500).end();
        }

    }
}
