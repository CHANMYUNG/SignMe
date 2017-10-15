package com.nooheat.controller.account.forget;

import com.nooheat.manager.RequestManager;
import com.nooheat.manager.UserManager;
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
 * Created by NooHeat on 15/10/2017.
 */
@API(category = Category.ACCOUNT, summary = "아이디 찾기", params = "email : String, type : String(ADMIN, USER)", response = "{ \"id\" : id값 }", etc = "없는 이메일 : 204, 서버 오류 : 500, 잘못된 요청 : 400", successCode = 200, failureCode = 204)
@URIMapping(uri = "/forget/id", method = HttpMethod.GET)
public class ForgetId implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();

        String email = req.getParam("email");
        String type = req.getParam("type");
        System.out.println(email);
        System.out.println(type);

        System.out.println(type.toUpperCase() != "ADMIN" && type.toUpperCase() != "USER");
        if (RequestManager.paramValidationCheck(email, type) == false) {
            res.setStatusCode(400).end();
        } else if (!type.toUpperCase().equals("ADMIN") && !type.toUpperCase().equals("USER")) {
            res.setStatusCode(400).end();
        } else {
            try {
                String id = UserManager.getIdByEmail(email, type);
                if (id == null) {
                    res.setStatusCode(204).end();
                } else {
                    res.setStatusCode(200).end(new JsonObject().put("id", id).toString());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                res.setStatusCode(500).end();
            }
        }

    }
}
