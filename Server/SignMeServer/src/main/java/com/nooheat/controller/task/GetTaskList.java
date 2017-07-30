package com.nooheat.controller.task;

import com.nooheat.database.DBManager;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by NooHeat on 24/07/2017.
 */
@API(category = Category.TASK, summary = "일정 조회", successCode = 200, failureCode = 400)
@URIMapping(uri="/task", method = HttpMethod.GET)
public class GetTaskList implements Handler<RoutingContext>{

    @Override
    public void handle(RoutingContext context) {
        ResultSet rs = DBManager.execute("SELECT a.uid, a.name, startDate, endDate, title, summary from task as t LEFT JOIN admin as a ON a.uid = t.writter");
        JSONArray response = new JSONArray();
        try {
            while(rs.next()){
                JSONObject object = new JSONObject();
                object.put("uid", rs.getString("uid"));
                object.put("name", rs.getString("name"));
                object.put("startDate", rs.getString("startDate"));
                object.put("endDate", rs.getString("endDate"));
                object.put("title", rs.getString("title"));
                object.put("summary", rs.getString("summary"));
                response.put(object);
            }
            context.response().setStatusCode(200).end(response.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            context.response().setStatusCode(400).end();
        }
    }
}
