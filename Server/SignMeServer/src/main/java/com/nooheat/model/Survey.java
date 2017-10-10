package com.nooheat.model;

import com.nooheat.database.DBManager;
import com.nooheat.support.Category;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by NooHeat on 17/09/2017.
 */
public class Survey extends Letter implements Statistic {
    private final static String SURVEY_SAVE = "INSERT INTO survey(writerUid, title, summary, openDate, closeDate) VALUES(?,?,?,?,?);";
    private final static String QUESTION_SAVE = "INSERT INTO surveyQuestion(question) VALUES(?)";
    private final static String SURVEY_FINDALL = "SELECT s.letterNumber, s.title, s.writerUid, s.summary, s.openDate, s.closeDate, a.name AS writerName, (SELECT count(*)>=1 FROM surveyAnswer as SA WHERE SA.uid = ? AND SA.letterNumber = s.letterNumber) as isAnswered  FROM survey AS s LEFT JOIN ADMIN AS a ON s.writerUid = a.uid ORDER BY letterNumber;";
    private final static String SURVEY_FINDONE = "SELECT s.letterNumber, s.title, s.writerUid, s.summary, s.openDate, s.closeDate, a.name AS writerName FROM survey AS s LEFT JOIN ADMIN AS a ON s.writerUid = a.uid WHERE letterNumber = ?;";
    private final static String SURVEY_DELETE = "DELETE FROM survey WHERE letterNumber = ?";
    private final static String QUESTION_DELETE = "DELETE FROM surveyQuestion WHERE letterNumber = ?";
    private final static String ANSWER_SAVE = "INSERT INTO  surveyAnswer(uid, letterNumber, columnIndex, answer, answerDate) VALUES(?, ?, ?, ?, ?);";
    private final static String ANSWER_MODIFY = "UPDATE surveyAnswer SET answer = ?, answerDate = ? WHERE uid = ? AND letterNumber = ? AND columnIndex = ?;";
    private final static String COUNT_ANSWER = "SELECT count(distinct uid) as count FROM surveyAnswer WHERE letterNumber = ?;";
    private int letterNumber;
    private String summary;
    private List items;
    private String closeDate;
    private String writerName;
    private boolean isAnswered;

    public Survey() {
        this.type = Category.SURVEY;
    }

    public Survey(int letterNumber, String writerUid, String title, String summary, List items, String openDate, String closeDate) {
        this.type = Category.SURVEY;
        this.letterNumber = letterNumber;
        this.writerUid = writerUid;
        this.title = title;
        this.summary = summary;
        this.items = items;
        this.openDate = openDate;
        this.closeDate = closeDate;
    }

    public Survey(String writerUid, String title, String summary, List items, String openDate, String closeDate) {
        this.type = Category.SURVEY;
        this.writerUid = writerUid;
        this.title = title;
        this.summary = summary;
        this.items = items;
        this.openDate = openDate;
        this.closeDate = closeDate;
    }

    public boolean save() throws SQLException {
        boolean surveySave = DBManager.update(SURVEY_SAVE, writerUid, title, summary, openDate, closeDate) == 1;
        boolean questionSave = saveItems();

        return surveySave && questionSave;
    }

    public boolean saveItems() throws SQLException {

        for (Object question : items) {
            if (DBManager.update(QUESTION_SAVE, (String) question) != 1) return false;
        }

        return true;
    }

    public boolean saveUpdated() throws SQLException {
        boolean surveySave = DBManager.update(SURVEY_SAVE, writerUid, title, summary, openDate, closeDate) == 1;
        boolean isDeletedAll = DBManager.update(QUESTION_DELETE, this.letterNumber) != -1;
        boolean questionSave = saveItems();

        return surveySave && isDeletedAll && questionSave;
    }

    public static JsonArray findAll(String uid) throws SQLException {
        ResultSet rs = DBManager.execute(SURVEY_FINDALL, uid);
        JsonArray result = new JsonArray();
        while (rs.next()) {
            System.out.println("!@#!@#");
            JsonObject object = new JsonObject();
            object.put("letterNumber", rs.getInt("letterNumber"));
            object.put("title", rs.getString("title"));
            object.put("summary", rs.getString("summary"));
            object.put("openDate", rs.getString("openDate"));
            object.put("closeDate", rs.getString("closeDate"));
            object.put("writerName", rs.getString("writerName"));
            object.put("isAnswered", rs.getBoolean("isAnswered"));
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
            survey.setWriterName(rs.getString("writerName"));

            ResultSet inner_rs = DBManager.execute("SELECT columnIndex, question FROM surveyQuestion WHERE letterNumber = ?", rs.getInt("letterNumber"));

            List<String> items = new ArrayList<>();
            while (inner_rs.next()) {

                items.add(inner_rs.getString("question"));
            }

            survey.setItems(items);

            return survey;
        } else return null;
    }

    public static List<Survey> getSurveyList(String uid) throws SQLException {
        ResultSet rs = DBManager.execute(SURVEY_FINDALL, uid);

        List<Survey> surveys = new ArrayList<>();

        while (rs.next()) {
            Survey survey = new Survey();
            survey.setLetterNumber(rs.getInt("letterNumber"));
            survey.setWriterUid(rs.getString("writerUid"));
            survey.setTitle(rs.getString("title"));
            survey.setSummary(rs.getString("summary"));
            survey.setOpenDate(rs.getString("openDate"));
            survey.setCloseDate(rs.getString("closeDate"));
            survey.setWriterName(rs.getString("writerName"));
            survey.setAnswered(rs.getBoolean("isAnswered"));
            ResultSet inner_rs = DBManager.execute("SELECT columnIndex, question FROM surveyQuestion WHERE letterNumber = ?", rs.getInt("letterNumber"));

            List<String> items = new ArrayList<>();
            while (inner_rs.next()) {

                items.add(inner_rs.getString("question"));
            }

            survey.setItems(items);
            surveys.add(survey);
        }

        return surveys;
    }

    @Override
    public String toString() {
        return new JsonObject()
                .put("writerName", this.writerName)
                .put("type", this.type)
                .put("letterNumber", this.letterNumber)
                .put("writerUid", this.writerUid)
                .put("title", this.title)
                .put("summary", this.summary)
                .put("items", this.items)
                .put("openDate", this.openDate)
                .put("closeDate", this.closeDate)
                .put("isAnswered", this.isAnswered)
                .toString();
    }

    @Override
    public JsonObject toJson() {
        return new JsonObject()
                .put("writerName", this.writerName)
                .put("type", this.type)
                .put("letterNumber", this.letterNumber)
                .put("writerUid", this.writerUid)
                .put("title", this.title)
                .put("summary", this.summary)
                .put("items", this.items)
                .put("openDate", this.openDate)
                .put("isAnswered", this.isAnswered)
                .put("closeDate", this.closeDate);
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

    public boolean delete() throws SQLException {
        boolean areQuestionsDeleted = DBManager.update(QUESTION_DELETE, this.letterNumber) != -1;
        boolean isSurveyDeleted = DBManager.update(SURVEY_DELETE, this.letterNumber) != -1;

        return areQuestionsDeleted && isSurveyDeleted;
    }

    public boolean answer(String uid, List answers, String answerDate) throws SQLException {
        Iterator<Integer> iterator = answers.iterator();
        int columnIndex = 1;
        while (iterator.hasNext()) {
            int answer = iterator.next();

            if (DBManager.update(ANSWER_SAVE, uid, this.letterNumber, columnIndex++, answer, answerDate) == -1) {
                return false;
            }
        }

        return true;
    }

    public int getCountOfAnswers() throws SQLException {
        ResultSet rs = DBManager.execute(COUNT_ANSWER, this.letterNumber);

        if (rs.next()) {
            return rs.getInt("count");
        } else return -1;
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

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public int getLetterNumber() {
        return letterNumber;
    }

    public String getTitle() {
        return title;
    }

    public List getItems() {
        return items;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public String getOpenDate() {
        return openDate;
    }

    public String getSummary() {
        return summary;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    @Override
    public XSSFWorkbook getStatistic() {
        XSSFWorkbook statistic = new XSSFWorkbook();
        XSSFSheet sheet = statistic.createSheet("통계");
        XSSFRow rowHead = sheet.createRow(0);
        rowHead.createCell(0).setCellValue("제목");
        rowHead.createCell(1).setCellValue("작성자");
        rowHead.createCell(2).setCellValue("딥변 수");

        return statistic;
    }

    public boolean modifyAnswer(String uid, List answers, String answerDate) throws SQLException {
        Iterator<Integer> iterator = answers.iterator();
        int columnIndex = 1;
        while (iterator.hasNext()) {
            int answer = iterator.next();

            if (DBManager.update(ANSWER_MODIFY, answer, answerDate, uid, letterNumber, columnIndex++) == -1) {
                return false;
            }
        }

        return true;
    }
}
