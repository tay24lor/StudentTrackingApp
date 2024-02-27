package android.zybooks.studentprogressapplication;

import java.time.LocalDate;

public class Course {
    private LocalDate mStart;
    private LocalDate mEnd;
    private String mTitle;
    private String mStatus;
    private Instructor mInstructor;

    public Course(LocalDate start, LocalDate end, String title, String status, Instructor instructor) {
        mStart = start;
        mEnd = end;
        mTitle = title;
        mStatus = status;
        mInstructor = instructor;
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

    public void setmEnd(LocalDate end) {
        this.mEnd = end;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public Instructor getInstructor() {
        return mInstructor;
    }

    public void setInstructor(Instructor instructor) {
        mInstructor = instructor;
    }
}
