package android.zybooks.studentprogressapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "terms")
public class Term {

    @PrimaryKey(autoGenerate = true)
    private int termId;
    private String mStart;
    private String mEnd;
    private String mTitle;

    public Term(int id, String title, String start, String end) {
        termId = id;
        mTitle = title;
        mStart = start;
        mEnd = end;
    }

    public int getId() {
        return termId;
    }

    public void setId(int id) {
        this.termId = id;
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
