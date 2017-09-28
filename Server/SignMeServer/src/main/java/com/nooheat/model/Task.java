package com.nooheat.model;

import com.nooheat.database.DBManager;
import io.vertx.core.json.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by NooHeat on 18/08/2017.
 */
public class Task {
    private static final String SAVE = "INSERT INTO TASK(writerUid, title, summary, startDate, endDate) VALUES(?,?,?,?,?);";
    private static final String FINDALL = "SELECT t.tid, a.uid as writerUid, a.name as writerName, startDate, endDate, title FROM TASK AS t LEFT JOIN ADMIN AS a ON a.uid = t.writerUid";
    private static final String FINDONE = "SELECT t.tid, a.uid as writerUid, a.name as writerName, startDate, endDate, title, summary FROM TASK AS t LEFT JOIN ADMIN AS a ON a.uid = t.writerUid WHERE tid = ?";
    private static final String UPDATE = "UPDATE TASK SET title = ?, summary = ?, startDate = ?, endDate = ? WHERE tid = ?";
    private static final String DELETE = "DELETE FROM TASK WHERE tid = ?";
    private String writerUid;
    private String writerName;
    private String title;
    private String summary;
    private String startDate;
    private String endDate;
    private int tid;

    public Task(int tid, String writerUid, String writerName, String title, String summary, String startDate, String endDate) {
        this.writerUid = writerUid;
        this.tid = tid;
        this.writerName = writerName;
        this.title = title;
        this.summary = summary;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Task(String writerUid, String title, String summary, String startDate, String endDate) {
        this(-1, writerUid, null, title, summary, startDate, endDate);
    }

    public static JSONArray findAll() {
        ResultSet rs = DBManager.execute(FINDALL);
        JSONArray response = null;
        try {
            while (rs.next()) {
                response = new JSONArray();
                JSONObject object = new JSONObject();
                object.put("tid", rs.getString("tid"));
                object.put("writerUid", rs.getString("writerUid"));
                object.put("writerName", rs.getString("writerName"));
                object.put("startDate", rs.getString("startDate"));
                object.put("endDate", rs.getString("endDate"));
                object.put("title", rs.getString("title"));
                response.put(object);
            }
            return response;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static JSONArray findByYear(int year) {
        String like = year + "%";

        ResultSet rs = DBManager.execute("SELECT t.tid, a.uid as writerUid, a.name as writerName, startDate, endDate, title FROM TASK AS t LEFT JOIN ADMIN AS a ON a.uid = t.writerUid where startDate like ? OR endDate like ?", like, like);
        JSONArray response = null;
        try {
            while (rs.next()) {
                response = new JSONArray();
                JSONObject object = new JSONObject();
                object.put("tid", rs.getString("tid"));
                object.put("writerUid", rs.getString("writerUid"));
                object.put("writerName", rs.getString("writerName"));
                object.put("startDate", rs.getString("startDate"));
                object.put("endDate", rs.getString("endDate"));
                object.put("title", rs.getString("title"));
                response.put(object);
            }
            return response;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static JSONArray findByYearAndMonth(int year, int month) {
        String like = month >= 10 ? year + "-" + month + "%" : year + "-0" + month + "%";

        ResultSet rs = DBManager.execute("SELECT t.tid, a.uid as writerUid, a.name as writerName, startDate, endDate, title FROM TASK AS t LEFT JOIN ADMIN AS a ON a.uid = t.writerUid where startDate like ? OR endDate like ?", like, like);
        JSONArray response = null;
        try {
            while (rs.next()) {
                response = new JSONArray();
                JSONObject object = new JSONObject();
                object.put("tid", rs.getString("tid"));
                object.put("writerUid", rs.getString("writerUid"));
                object.put("writerName", rs.getString("writerName"));
                object.put("startDate", rs.getString("startDate"));
                object.put("endDate", rs.getString("endDate"));
                object.put("title", rs.getString("title"));
                response.put(object);
            }
            return response;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static Task findOne(int tid) {
        ResultSet rs = DBManager.execute(FINDONE, tid);

        try {
            rs.next();
            if (rs.getString("title") != null) {

                String writerUid = rs.getString("writerUid");
                String writerName = rs.getString("writerName");
                String startDate = rs.getString("startDate");
                String endDate = rs.getString("endDate");
                String title = rs.getString("title");
                String summary = rs.getString("summary");

                return new Task(tid, writerUid, writerName, title, summary, startDate, endDate);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean save() throws SQLException {
        return DBManager.update(SAVE, writerUid, title, summary, startDate, endDate) == 1;
    }

    public boolean isDuplicated() {
        ResultSet rs = DBManager.execute("SELECT COUNT(*) AS COUNT FROM TASK WHERE title = ? AND startDate = ? AND endDate = ?", title, startDate, endDate);

        try {
            rs.next();
            if (rs.getInt("COUNT") == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public void update(String title, String summary, String startDate, String endDate) {
        this.title = title;
        this.summary = summary;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean saveUpdated() throws SQLException {
        return DBManager.update(UPDATE, title, summary, startDate, endDate, tid) == 1;
    }

    public boolean delete() throws SQLException {
        return DBManager.update(DELETE, tid) != -1;
    }

    @Override
    public String toString() {
        return new JsonObject()
                .put("tid", tid)
                .put("writerUid", writerUid)
                .put("writerName", writerName)
                .put("title", title)
                .put("summary", title)
                .put("startDate", title)
                .put("endDate", title)
                .toString();
    }

    public String getWriterUid() {
        return writerUid;
    }

    public String getWriterName() {
        return writerName;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getTid() {
        return tid;
    }
}
