package com.nooheat.controller.answer;

import com.nooheat.manager.JWT;
import com.nooheat.model.Survey;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.sql.SQLException;

/**
 * Created by NooHeat on 25/09/2017.
 */
@API(category = Category.SURVEY, summary = "설문조사 응답현황 보기", successCode = 200, failureCode = 400, etc = "잘못된 요청 : 400, 비로그인 : 401")
@URIMapping(uri = "/status/survey/:letterNumber", method = HttpMethod.GET)
public class GetSurveyStatus implements Handler<RoutingContext> {
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

        int letterNumber = -1;

        try {
            letterNumber = Integer.parseInt(req.getParam("letterNumber"));
        } catch (NumberFormatException e) {
            res.setStatusCode(400).end();
            return;
        }

        Survey survey = null;
        try {
            survey = Survey.findOne(letterNumber);

            if (survey == null) {
                res.setStatusCode(400).end();
                return;
            }

            int count = survey.getCountOfAnswers();

            JsonObject response = new JsonObject()
                    .put("letterNumber", survey.getLetterNumber())
                    .put("count", count)
                    .put("writerName", survey.getWriterName())
                    .put("title", survey.getTitle())
                    .put("openDate", survey.getOpenDate())
                    .put("closeDate", survey.getCloseDate())
                    .put("summary", survey.getSummary());


            res.setStatusCode(200).end(response.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            res.setStatusCode(500).end();
            return;
        }
    }
}
