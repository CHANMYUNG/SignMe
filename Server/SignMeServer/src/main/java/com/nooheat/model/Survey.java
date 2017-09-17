package com.nooheat.model;

import com.nooheat.database.DBManager;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by NooHeat on 17/09/2017.
 */
public class Survey {
    private final static String SURVEY_SAVE = "INSERT INTO survey(writerUid, title, summary, openDate, closeDate) VALUES(?,?,?,?,?);";
    private final static String QUESTION_SAVE = "INSERT INTO surveyQuestion(question) VALUES(?)";
    private final static String SURVEY_FINDALL = "SELECT * FROM survey ORDER BY letterNumber;";
    int letterNumber;
    String title;
    String writerUid;
    String summary;
    List items;
    String openDate;
    String closeDate;

    public Survey(int letterNumber, String writerUid, String title, String summary, List items, String openDate, String closeDate) {
        this.letterNumber = letterNumber;
        this.writerUid = writerUid;
        this.title = title;
        this.summary = summary;
        this.items = items;
        this.openDate = openDate;
        this.closeDate = closeDate;
    }

    public Survey(String writerUid, String title, String summary, List items, String openDate, String closeDate) {
        this.writerUid = writerUid;
        this.title = title;
        this.summary = summary;
        this.items = items;
        this.openDate = openDate;
        this.closeDate = closeDate;
    }

    public boolean save() {
        boolean surveySave = DBManager.update(SURVEY_SAVE, writerUid, title, summary, openDate, closeDate) == 1;
        boolean questionSave = saveItems();

        return surveySave && questionSave;
    }

    public boolean saveItems() {

        for (Object question : items) {
            if (DBManager.update(QUESTION_SAVE, (String) question) != 1) return false;
        }

        return true;
    }

    public static JsonArray findAll() throws SQLException {
        ResultSet rs = DBManager.execute(SURVEY_FINDALL);
        JsonArray result = new JsonArray();
        while (rs.next()) {
            JsonObject object = new JsonObject();
            object.put("letterNumber", rs.getInt("letterNumber"));
            object.put("title", rs.getString("title"));
            object.put("summary", rs.getString("summary"));
            object.put("openDate", rs.getString("openDate"));
            object.put("closeDate", rs.getString("closeDate"));

            ResultSet inner_rs = DBManager.execute("SELECT columnIndex, question FROM surveyQuestion WHERE letterNumber = ?", object.getInteger("letterNumber"));

            JsonArray items = new JsonArray();
            while (inner_rs.next()) {

                items.add(inner_rs.getString("question"));
            }
            object.put("items", items);
            result.add(object);
        }

        return result;
    }
}
