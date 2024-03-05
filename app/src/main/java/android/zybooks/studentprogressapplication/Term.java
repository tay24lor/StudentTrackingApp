package android.zybooks.studentprogressapplication;

import android.content.Context;

import java.time.LocalDate;
import java.util.List;

public class Term {
    private int mId;
    private String mStart;
    private String mEnd;
    private String mTitle;
    private final List<Course> mCourses;

    public Term(int id, String start, String end, String title, List<Course> courses) {
        mId = id;
        mStart = start;
        mEnd = end;
        mTitle = title;
        mCourses = courses;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
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

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public List<Course> getCourses() {
        return mCourses;
    }
    public void setCourse(Course course) {
        mCourses.add(course);
    }
}
