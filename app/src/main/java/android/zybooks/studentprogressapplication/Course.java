package android.zybooks.studentprogressapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int primary_id;
    private String start;
    private String end;
    private String title;
    private String status;

    private String instructorName;
    private String instructorPhone;
    private String instructorEmail;
    private int termID;
    private String notes;

    public Course(int id, String title, String start, String end, String status, String instructorName,
                  String instructorPhone, String instructorEmail, int termId, String notes) {
        primary_id = id;
        this.start = start;
        this.end = end;
        this.title = title;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.termID = termId;
        this.notes = notes;
    }

    public Course(){}

    public int getPrimary_id() {
        return primary_id;
    }

    public void setPrimary_id(int primary_id) {
        this.primary_id = primary_id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTermID() { return termID; }

    public void setTermID(int id) { termID = id; }

    public String getInstructorName() {
        return this.instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) { this.instructorPhone = instructorPhone; }

    public String getInstructorEmail() { return instructorEmail; }

    public void setInstructorEmail(String instructorEmail) { this.instructorEmail = instructorEmail; }

    public void setNotes(String note) {
        this.notes = note;
    }
    public String getNotes() {
        return this.notes;
    }
}
