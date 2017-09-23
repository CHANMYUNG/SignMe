package com.nooheat.model;

import com.nooheat.database.DBManager;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by NooHeat on 17/09/2017.
 */
public class Survey extends Letter {
    private final static String SURVEY_SAVE = "INSERT INTO survey(writerUid, title, summary, openDate, closeDate) VALUES(?,?,?,?,?);";
    private final static String QUESTION_SAVE = "INSERT INTO surveyQuestion(question) VALUES(?)";
    private final static String SURVEY_FINDALL = "SELECT * FROM survey ORDER BY letterNumber;";
    private final static String SURVEY_FINDONE = "SELECT * FROM survey WHERE letterNumber = ?;";
    private final static String SURVEY_DELETE = "DELETE FROM survey WHERE letterNumber = ?";
    private final static String QUESTION_DELETE = "DELETE FROM surveyQuestion WHERE letterNumber = ?";
    private final static String ANSWER_SAVE = "INSERT INTO  surveyAnswer(uid, letterNumber, columnIndex, answer, answerDate) VALUES(?, ?, ?, ?, ?);";
    private int letterNumber;
    private String title;
    private String writerUid;
    private String summary;
    private List items;
    private String closeDate;

    public Survey() {
    }

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

    public boolean saveUpdated() {
        boolean surveySave = DBManager.update(SURVEY_SAVE, writerUid, title, summary, openDate, closeDate) == 1;
        boolean isDeletedAll = DBManager.update(QUESTION_DELETE, this.letterNumber) != -1;
        boolean questionSave = saveItems();

        return surveySave && isDeletedAll && questionSave;
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

    public static Survey findOne(int letterNumber) throws SQLException {
        ResultSet rs = DBManager.execute(SURVEY_FINDONE, letterNumber);
        if (rs.next()) {
            Survey survey = new Survey();
            survey.setLetterNumber(rs.getInt("letterNumber"));
            survey.setWriterUid(rs.getString("writerUid"));
            survey.setTitle(rs.getString("title"));
            survey.setSummary(rs.getString("summary"));
            survey.setOpenDate(rs.getString("openDate"));
            survey.setCloseDate(rs.getString("closeDate"));

            ResultSet inner_rs = DBManager.execute("SELECT columnIndex, question FROM surveyQuestion WHERE letterNumber = ?", rs.getInt("letterNumber"));

            List<String> items = new ArrayList<>();
            while (inner_rs.next()) {

                items.add(inner_rs.getString("question"));
            }

            survey.setItems(items);

            return survey;
        } else return null;
    }

    @Override
    public String toString() {
        return new JsonObject().put("letterNumber", this.letterNumber).put("writerUid", this.writerUid)
                .put("title", this.title)
                .put("summary", this.summary)
                .put("items", this.items)
                .put("openDate", this.openDate)
                .put("closeDate", this.closeDate)
                .toString();
    }

    public Survey update(String title, String summary, List items, String openDate, String closeDate) {
        this.title = title;
        this.summary = summary;
        this.items = items;
        this.openDate = openDate;
        this.closeDate = closeDate;

        return this;
    }

    public String getWriterUid() {
        return this.writerUid;
    }

    public boolean delete() {
        boolean areQuestionsDeleted = DBManager.update(QUESTION_DELETE, this.letterNumber) != -1;
        boolean isSurveyDeleted = DBManager.update(SURVEY_DELETE, this.letterNumber) != -1;

        return areQuestionsDeleted && isSurveyDeleted;
    }

    public boolean answer(String uid, List answers, String answerDate) {
        Iterator<Integer> iterator = answers.iterator();
        int columnIndex = 1;
        while(iterator.hasNext()){
            int answer = iterator.next();

            if(DBManager.update(ANSWER_SAVE, uid, this.letterNumber, columnIndex++, answer, answerDate) == -1){
                return false;
            }
        }

        return true;
    }

    public void setLetterNumber(int letterNumber) {
        this.letterNumber = letterNumber;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWriterUid(String writerUid) {
        this.writerUid = writerUid;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }
}
