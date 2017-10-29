package com.nooheat.controller.letter.survey;

import com.nooheat.manager.JWT;
import com.nooheat.model.Survey;
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
 * Created by NooHeat on 23/09/2017.
 */
@API(category = Category.SURVEY, summary = "설문조사 조회", successCode = 200, response = "{letterNumber:1, title:앙, summary:브로들, openDate:2017-09-17 00:00:00, closeDate:2017-09-27 00:00:00, items:[이잉, 이잉2], answerForms: [[]]}", failureCode = 500, etc = "비로그인 : 401, 없는 letterNumber : 400, 서버 오류 : 500")
@URIMapping(uri = "/survey/:letterNumber", method = HttpMethod.GET)
public class GetSurvey implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();

        JWT token = JWT.verify(ctx);

        if (token == null) {
            res.setStatusCode(401).end();
            return;
        }

        int letterNumber = -1;
        try {
            letterNumber = Integer.parseInt(req.getParam("letterNumber"));
        } catch (NumberFormatException e){
            res.setStatusCode(400).end();
        }

        Survey survey = null;

        try {
            survey = Survey.findOne(letterNumber);
        } catch (SQLException e) {
            e.printStackTrace();
            res.setStatusCode(500).end();

            return;
        }

        if(survey == null){
            res.setStatusCode(400).end();
        }

        else res.setStatusCode(200).end(survey.toString());


    }
}
