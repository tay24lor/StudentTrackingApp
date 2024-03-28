package android.zybooks.studentprogressapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "terms")
public class Term {

    @PrimaryKey(autoGenerate = true)
    private int primary_id;
    private String title;
    private String start;
    private String end;

    public Term(int id, String title, String start, String end) {
        primary_id = id;
        this.title = title;
        this.start = start;
        this.end = end;
    }

    public Term() {}

    public int getPrimary_id() {
        return primary_id;
    }

    public void setPrimary_id(int id) {
        primary_id = id;
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

}
