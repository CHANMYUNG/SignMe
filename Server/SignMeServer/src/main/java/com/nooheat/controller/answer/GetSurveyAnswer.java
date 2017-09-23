package com.nooheat.controller.answer;

import com.nooheat.database.DBManager;
import com.nooheat.manager.JWT;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.RoutingContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NooHeat on 24/09/2017.
 */
@API(category = Category.SURVEY, summary = "설문조사 응답 보기", response = "[1, 2]", successCode = 201, failureCode = 400, etc = "잘못된 요청 : 400, 비로그인 : 401, 관리자인 경우 : 403, 서버오류 : 500")
@URIMapping(uri = "/answer/survey/:letterNumber", method = HttpMethod.GET)
public class GetSurveyAnswer implements Handler<RoutingContext> {
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
        } catch (NumberFormatException e) {
            res.setStatusCode(400).end();
            return;
        }

        ResultSet rs = DBManager.execute("SELECT * FROM surveyAnswer WHERE letterNumber = ? AND uid = ? ORDER BY columnIndex;",letterNumber, token.getUid());

        List<Integer> answers = new ArrayList<>();
        try {
            while(rs.next()){
                answers.add(rs.getInt("answer"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            res.setStatusCode(500).end();
            return;
        }

        res.setStatusCode(200).end(answers.toString());
    }
}
