package android.zybooks.studentprogressapplication;

import java.time.LocalDate;

public class Term {
    private LocalDate mStart;
    private LocalDate mEnd;
    private String mTitle;

    public Term(LocalDate start, LocalDate end, String title) {
        mStart = start;
        mEnd = end;
        mTitle = title;
    }

    public LocalDate getStart() {
        return mStart;
    }

    public void setStart(LocalDate start) {
        this.mStart = start;
    }

    public LocalDate getEnd() {
        return mEnd;
    }

    public void setEnd(LocalDate end) {
        this.mEnd = end;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }
}
