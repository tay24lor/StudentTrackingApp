package android.zybooks.studentprogressapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "terms")
public class Term {

    @PrimaryKey(autoGenerate = true)
    private int primary_id;
    private String mStart;
    private String mEnd;
    private String mTitle;
    public Term(int id, String title, String start, String end) {
        primary_id = id;
        mTitle = title;
        mStart = start;
        mEnd = end;
    }

    public Term() {

    }

    public int getPrimary_id() {
        return primary_id;
    }

    public void setPrimary_id(int id) {
        primary_id = id;
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

}
