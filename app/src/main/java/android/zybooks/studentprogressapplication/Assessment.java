package android.zybooks.studentprogressapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey
    private int assessmentID;
    private String type;
    private String title;
    private String start;
    private String end;

    private int courseID;

    public Assessment() {}

    public Assessment(int id, String type, String start, String end, int courseID) {
        assessmentID = id;
        this.type = type;
        this.start = start;
        this.end = end;
        this.courseID = courseID;
    }
    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
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

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
