package android.zybooks.studentprogressapplication.Database;

import android.app.Application;
import android.zybooks.studentprogressapplication.Assessment;
import android.zybooks.studentprogressapplication.Course;
import android.zybooks.studentprogressapplication.DAO.AssessmentDAO;
import android.zybooks.studentprogressapplication.DAO.CourseDAO;
import android.zybooks.studentprogressapplication.DAO.TermDAO;
import android.zybooks.studentprogressapplication.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private final TermDAO mTermDAO;
    private final CourseDAO mCourseDAO;
    private final AssessmentDAO mAssessmentDAO;
    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Assessment> mAllAssessments;



    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        TermDatabaseBuilder tDB = TermDatabaseBuilder.getDatabase(application);
        mTermDAO = tDB.termDAO();
        mCourseDAO = tDB.courseDAO();
        mAssessmentDAO = tDB.assessmentDAO();
    }

    public List<Term> getAllTerms() {
        databaseExecutor.execute(() -> mAllTerms = mTermDAO.getTerms());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }
    public void insert(Term term){
        databaseExecutor.execute(()-> mTermDAO.insert(term));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Term term){
        databaseExecutor.execute(()-> mTermDAO.update(term));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Term term){
        databaseExecutor.execute(()-> mTermDAO.delete(term));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public List<Course> getAllCourses() {
        databaseExecutor.execute(()-> mAllCourses = mCourseDAO.getAllCourses());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }
    public void insert(Course course) {
        databaseExecutor.execute(()-> mCourseDAO.insert(course));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Course course) {
        databaseExecutor.execute(()-> mCourseDAO.update(course));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Course course) {
        databaseExecutor.execute(()-> mCourseDAO.delete(course));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public List<Assessment> getAllAssessments() {
        databaseExecutor.execute(() -> mAllAssessments = mAssessmentDAO.getAllAssessments());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }
    public void insert(Assessment assessment){
        databaseExecutor.execute(()-> mAssessmentDAO.insert(assessment));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Assessment assessment){
        databaseExecutor.execute(()-> mAssessmentDAO.update(assessment));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Assessment assessment){
        databaseExecutor.execute(()-> mAssessmentDAO.delete(assessment));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
