package com.nooheat.controller.letter.survey;

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
 * Created by NooHeat on 04/09/2017.
 */

@API(category = Category.SURVEY, summary = "설문조사 생성", requestBody = "title : String, summary : String, items : List, openDate : String, closeDate : String", successCode = 201, failureCode = 400, etc = "잘못된 요청 : 400, 비로그인 : 401")
@URIMapping(uri = "/survey", method = HttpMethod.POST)
public class PostSurvey implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext ctx) {
        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();
        JWT token = JWT.verify(ctx);

        if (token == null) {
            res.setStatusCode(401).end();
            return;
        }

        if (!token.isAdmin()) {
            res.setStatusCode(403).end();
            return;
        }

        System.out.println(req.formAttributes().toString());
        System.out.println(req.toString());

        String writerUid = token.getUid();
        String title = req.getFormAttribute("title");
        String summary = req.getFormAttribute("summary");
        String openDate = req.getFormAttribute("openDate");
        String closeDate = req.getFormAttribute("closeDate");
        String itemString = req.getFormAttribute("items");

        if (RequestManager.paramValidationCheck(writerUid, title, summary, openDate, closeDate, itemString) == false) {
            res.setStatusCode(400).end();
            return;
        }

        List items = new JsonArray(itemString).getList();

        Survey survey = new Survey(writerUid, title, summary, items, openDate, closeDate);

        boolean result = false;
        try {
            result = survey.save();
            if (result) res.setStatusCode(201).end();
            else res.setStatusCode(400).end();
        } catch (SQLException e) {
            e.printStackTrace();
            res.setStatusCode(500).end();
        }


    }
}