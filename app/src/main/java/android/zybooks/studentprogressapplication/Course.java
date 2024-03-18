package android.zybooks.studentprogressapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int primary_id;
    private String mStart;
    private String mEnd;
    private String mTitle;
    private String mStatus;
    private String instructorName;
    private int termID;

    public Course(int id, String title, String start, String end, String status, String instructorName) {
        primary_id = id;
        mStart = start;
        mEnd = end;
        mTitle = title;
        mStatus = status;
        this.instructorName = instructorName;
    }

    public Course(){}

    public int getPrimary_id() {
        return primary_id;
    }

    public void setPrimary_id(int primary_id) {
        this.primary_id = primary_id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getStart() {
        return mStart;
    }

    public void setStart(String start) {
        this.mStart = start;
    }

    public String getEnd() {
        return mEnd;
    }

    public void setEnd(String end) {
        this.mEnd = end;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public int getTermID() { return termID; }

    public void setTermID(int id) { termID = id; }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }
}
