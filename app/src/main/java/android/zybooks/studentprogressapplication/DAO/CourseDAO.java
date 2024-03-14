package android.zybooks.studentprogressapplication.DAO;

import android.zybooks.studentprogressapplication.Course;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM COURSES ORDER BY courseID Asc")
    List<Course> getAllCourses();

    @Query("SELECT * FROM COURSES WHERE termID=:term ORDER BY courseID Asc")
    List<Course> getAssociatedCourses(int term);
}
