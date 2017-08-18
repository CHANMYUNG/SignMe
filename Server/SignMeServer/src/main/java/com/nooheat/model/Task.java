package com.nooheat.model;

import com.nooheat.database.DBManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by NooHeat on 18/08/2017.
 */
public class Task {
    private static final String SAVE = "INSERT INTO TASK(writer, title, summary, startDate, endDate) VALUES(?,?,?,?,?);";
    private static final String FINDALL = "SELECT t.tid, a.uid as writerUid, a.name, startDate, endDate, title FROM TASK AS t LEFT JOIN ADMIN AS a ON a.uid = t.writer";
    private static final String FINDONE = "SELECT t.tid, a.uid as writerUid, a.name, startDate, endDate, title, summary FROM TASK AS t LEFT JOIN ADMIN AS a ON a.uid = t.writer WHERE tid = ?";
    private static final String UPDATE = "UPDATE TASK SET title = ?, summary = ?, startDate = ?, endDate = ? WHERE tid = ?";

    private String writerUid;
    private String writer;
    private String title;
    private String summary;
    private String startDate;
    private String endDate;
    private int tid;

    public Task(String writerUid, int tid, String writer, String title, String summary, String startDate, String endDate) {
        this.writerUid = writerUid;
        this.tid = tid;
        this.writer = writer;
        this.title = title;
        this.summary = summary;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Task(String writer, String title, String summary, String startDate, String endDate) {
        this(null, -1, writer, title, summary, startDate, endDate);
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
                object.put("writer", rs.getString("name"));
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
                JSONObject object = new JSONObject();

                String writerUid = rs.getString("writerUid");
                String writer = rs.getString("name");
                String startDate = rs.getString("startDate");
                String endDate = rs.getString("endDate");
                String title = rs.getString("title");
                String summary = rs.getString("summary");

                return new Task(writerUid, tid, writer, title, summary, startDate, endDate);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean save() {
        return DBManager.update(SAVE, writer, title, summary, startDate, endDate) != -1;
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

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();

        obj.put("tid", tid);
        obj.put("writerUid", writerUid);
        obj.put("writer", writer);
        obj.put("title", title);
        obj.put("summary", title);
        obj.put("startDate", title);
        obj.put("endDate", title);

        return obj.toString();
    }

    public String getWriterUid() {
        return writerUid;
    }

    public String getWriter() {
        return writer;
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
