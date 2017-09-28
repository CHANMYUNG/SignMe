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
 * Created by NooHeat on 23/09/2017.
 */
@API(category = Category.SURVEY, summary = "설문조사 조회", requestBody = "title : String, summary : String, items : List, openDate : String, closeDate : String", successCode = 200, failureCode = 400, etc = "없는 letterNumber : 400, 비로그인 : 401, 관리자 아님 :403, 서버 오류 : 500")
@URIMapping(uri = "/survey/:letterNumber", method = HttpMethod.PUT)
public class PutSurvey implements Handler<RoutingContext> {
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
            String title = req.getFormAttribute("title");
            String summary = req.getFormAttribute("summary");
            String openDate = req.getFormAttribute("openDate");
            String closeDate = req.getFormAttribute("closeDate");
            List items = new JsonArray(req.getFormAttribute("items")).getList();

            if (RequestManager.paramValidationCheck(title, summary, openDate, closeDate, items) == false) {
                res.setStatusCode(400).end();
                return;
            }
            Survey survey = null;
            try {
                survey = Survey.findOne(letterNumber);
            } catch (SQLException e) {
                res.setStatusCode(500).end();
                return;
            }

            if (survey == null) {
                res.setStatusCode(400).end();
                return;
            }

            if (!survey.getWriterUid().equals(token.getUid())) {
                res.setStatusCode(403).end();
                return;
            }

            boolean success = survey.update(title, summary, items, openDate, closeDate).saveUpdated();

            if (success) {
                res.setStatusCode(200).end();
            } else {
                res.setStatusCode(500).end();
            }
        } catch (NumberFormatException e) {
            res.setStatusCode(400).end();
        } catch (SQLException exception) {
            res.setStatusCode(500).end();
        }

    }
}
