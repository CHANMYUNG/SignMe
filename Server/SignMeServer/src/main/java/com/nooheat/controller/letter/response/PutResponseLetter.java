package com.nooheat.controller.letter.response;

import com.nooheat.manager.JWT;
import com.nooheat.manager.RequestManager;
import com.nooheat.model.ResponseLetter;
import com.nooheat.model.Task;
import com.nooheat.support.*;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

import java.sql.SQLException;

/**
 * Created by NooHeat on 15/10/2017.
 */
@API(category = Category.RESPONSELETTER, summary = "응답형 가정통신문 수정", successCode = 200, failureCode = 400, etc = "잘못된 요청 : 400, 비로그인 : 401, 권한 없음(관리자 아님) : 403")
@URIMapping(uri = "/letter/response/:letterNumber", method = HttpMethod.PUT)
public class PutResponseLetter implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();

        JWT token = JWT.verify(ctx);

        if (token == null) {
            res.setStatusCode(401).end();
            return;
        }
        System.out.println(token.isAdmin());
        if (token.isAdmin() == false) {
            res.setStatusCode(403).end();
            return;
        }
        int letterNumber = -1;
        try {
            letterNumber = Integer.parseInt(req.getParam("letterNumber"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            res.setStatusCode(400).end();
            return;
        }

        String writerUid = token.getUid();
        String title = req.getFormAttribute("title");
        String contents = req.getFormAttribute("contents");
        String openDate = DateTime.getDateNow();
        String closeDate = req.getFormAttribute("closeDate");
        if (RequestManager.paramValidationCheck(writerUid, title, contents, closeDate) == false) {
            res.setStatusCode(400).end();
            return;
        }

        //Task task = new Task(writerUid, title, "", openDate, closeDate, TaskColor.geneateColorCode(), "RESPONSE");

        try {
            ResponseLetter letter = ResponseLetter.findOne(letterNumber);
            letter.setTitle(title);
            letter.setContents(contents);
            letter.setOpenDate(openDate);
            letter.setCloseDate(closeDate);
            letter.setWriterUid(writerUid);

            Task task = Task.findOne("RESPONSE", letterNumber);
            task.update(title, "", openDate, closeDate);


            boolean success = letter.saveUpdated();
            boolean taskSaved = task.saveUpdated();

            // TODO : taskSaved 사용
            if (success && taskSaved) res.setStatusCode(200).end();
            else res.setStatusCode(400).end();

        } catch (SQLException e) {
            e.printStackTrace();
            res.setStatusCode(500).end();
        }
    }
}
