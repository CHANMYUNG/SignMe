package com.nooheat.controller.answer;

import com.nooheat.manager.JWT;
import com.nooheat.manager.RequestManager;
import com.nooheat.model.Survey;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.List;


/**
 * Created by NooHeat on 23/09/2017.
 */
@API(category = Category.SURVEY, summary = "설문조사 생성", requestBody = "answers : [], answerDate : String", successCode = 201, failureCode = 400, etc = "잘못된 요청 : 400, 비로그인 : 401")
@URIMapping(uri = "/answer/survey/:letterNumber", method = HttpMethod.POST)
public class ToSurvey implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        HttpServerResponse res = ctx.response();
        HttpServerRequest req = ctx.request();
        JWT token = JWT.verify(ctx);

        if (token == null) {
            res.setStatusCode(401).end();
            return;
        }

        if (token.isAdmin()) {
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

        String uid = token.getUid();

        List answers = new JsonArray(req.getFormAttribute("answers")).getList();
        String answerDate = req.getFormAttribute("answerDate");

        if (RequestManager.paramValidationCheck(answers, answerDate) == false) {
            res.setStatusCode(400).end();
            return;
        }

        Survey survey = null;

        try {
            survey = Survey.findOne(letterNumber);
        } catch (SQLException e) {
            e.printStackTrace();
            res.setStatusCode(500).end();
            return;
        }

        if (survey == null) {
            res.setStatusCode(400).end();
            return;
        }

        boolean success = survey.answer(uid, answers, answerDate);

        if (success) res.setStatusCode(200).end();
        else res.setStatusCode(500).end();
    }
}
