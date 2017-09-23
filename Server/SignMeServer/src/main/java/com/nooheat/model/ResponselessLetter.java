package com.nooheat.model;

import com.mysql.cj.api.mysqla.result.Resultset;
import com.nooheat.database.DBManager;
import com.nooheat.support.Category;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.ws.Response;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NooHeat on 10/09/2017.
 */

public class ResponselessLetter extends Letter {

    final static String FINDALL = "SELECT * FROM responselessLetter";
    final static String SAVE = "INSERT INTO responselessLetter(writerUid, title, contents, openDate) VALUES(?, ?, ?, ?)";
    final static String FINDONE = "SELECT * FROM responselessLetter WHERE letterNumber = ?";
    final static String UPDATE = "UPDATE responselessLetter SET title = ?, contents = ?, openDate = ? WHERE letterNumber = ?";
    final static String DELETE = "DELETE FROM responselessLetter WHERE letterNumber = ?";

    int letterNumber;

    public int getLetterNumber() {
        return letterNumber;
    }

    public String getWriterUid() {
        return writerUid;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getOpenDate() {
        return openDate;
    }

    String writerUid;
    String title;
    String contents;

    public ResponselessLetter() {
        this.type = Category.RESPONSELESSLETTER;
    }

    public ResponselessLetter(int letterNumber, String writerUid, String title, String contents, String openDate) {
        this.type = Category.RESPONSELESSLETTER;
        this.letterNumber = letterNumber;
        this.writerUid = writerUid;
        this.title = title;
        this.contents = contents;
        this.openDate = openDate;
    }


    public ResponselessLetter(String writerUid, String title, String contents, String openDate) {
        this.type = Category.RESPONSELESSLETTER;
        this.writerUid = writerUid;
        this.title = title;
        this.contents = contents;
        this.openDate = openDate;
    }

    public static JsonArray findAll() {

        JsonArray response = null;

        ResultSet rs = DBManager.execute(FINDALL);

        try {
            while (rs.next()) {
                response = new JsonArray();
                JsonObject object = new JsonObject();
                object.put("letterNumber", rs.getString("letterNumber"));
                object.put("writerUid", rs.getString("writerUid"));
                object.put("title", rs.getString("title"));
                object.put("contents", rs.getString("contents"));
                object.put("openDate", rs.getString("openDate"));
                response.add(object);
            }
            return response;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return response;
    }


    public static ResponselessLetter findOne(int letterNumber) {
        ResultSet rs = DBManager.execute(FINDONE, letterNumber);

        try {
            if (rs.next()) {

                String writerUid = rs.getString("writerUid");
                String title = rs.getString("title");
                String contents = rs.getString("contents");
                String openDate = rs.getString("openDate");

                return new ResponselessLetter(letterNumber, writerUid, title, contents, openDate);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean save() {
        return DBManager.update(SAVE, writerUid, title, contents, openDate) == 1;
    }

    public ResponselessLetter update(String title, String contents) {
        this.title = title;
        this.contents = contents;

        return this;
    }

    public static List<ResponselessLetter> getLetterList() throws SQLException {
        ResultSet rs = DBManager.execute(FINDALL);
        List<ResponselessLetter> letters = new ArrayList<>();
        while (rs.next()) {
            int letterNumber = rs.getInt("letterNumber");
            String writerUid = rs.getString("writerUid");
            String title = rs.getString("title");
            String contents = rs.getString("contents");
            String openDate = rs.getString("openDate");

            ResponselessLetter letter = new ResponselessLetter(letterNumber, writerUid, title, contents, openDate);

            letters.add(letter);
        }

        return letters;
    }

    public boolean saveUpdated() {
        return DBManager.update(UPDATE, title, contents, openDate, letterNumber) == 1;
    }

    public boolean delete() {
        return DBManager.update(DELETE, letterNumber) != -1;
    }

    @Override
    public String toString() {
        return new JsonObject()
                .put("type", this.type.name())
                .put("letterNumber", this.letterNumber)
                .put("writerUid", this.writerUid)
                .put("title", this.title)
                .put("contents", this.contents)
                .put("openDate", this.openDate)
                .toString();
    }

    @Override
    public JsonObject toJson() {
        return new JsonObject()
                .put("type", this.type.name())
                .put("letterNumber", this.letterNumber)
                .put("writerUid", this.writerUid)
                .put("title", this.title)
                .put("contents", this.contents)
                .put("openDate", this.openDate);
    }

}
