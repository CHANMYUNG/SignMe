package com.nooheat.controller.letter;

import com.nooheat.database.DBManager;
import com.nooheat.manager.JWT;
import com.nooheat.model.Letter;
import com.nooheat.model.ResponselessLetter;
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by NooHeat on 24/09/2017.
 */
@API(category = Category.LETTER, summary = "모든 유인물 조회", successCode = 200, response = "[{type:RESPONSELESSLETTER, letterNumber:2, writerUid:2, title:보건교육, contents:성교육임ㅋ, openDate:2017-09-14}, {type:SURVEY, letterNumber:1, writerUid:2, title:앙, summary:브로들, items:[이잉, 이잉2], openDate:2017-09-17 00:00:00, closeDate:2017-09-27 00:00:00}]", failureCode = 500, etc = "비로그인 : 401, 없는 letterNumber : 400, 서버 오류 : 500")
@URIMapping(uri = "/letter", method = HttpMethod.GET)
public class GetLetterList implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext ctx) {
        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();

        JWT token = JWT.verify(ctx);

        if(token == null){
            res.setStatusCode(401).end();
            return;
        }

        List<Letter> letterList = new ArrayList<>();

        try {
            letterList.addAll(Survey.getSurveyList());
            letterList.addAll(ResponselessLetter.getLetterList());
        } catch (SQLException e) {
            e.printStackTrace();
            res.setStatusCode(500).end();
        }

        letterList.sort(new Comparator<Letter>() {
            @Override
            public int compare(Letter o1, Letter o2) {
                return o1.compareTo(o2);
            }
        });

        JsonArray list = new JsonArray();

        Iterator<Letter> iterator = letterList.iterator();

        while (iterator.hasNext()) {
            Letter letter = iterator.next();

            list.add(letter.toJson());
        }

        res.setStatusCode(200).end(list.toString());
    }
}
