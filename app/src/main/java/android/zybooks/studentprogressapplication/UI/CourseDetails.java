package android.zybooks.studentprogressapplication.UI;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.zybooks.studentprogressapplication.Course;
import android.zybooks.studentprogressapplication.Database.Repository;
import android.zybooks.studentprogressapplication.R;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CourseDetails extends AppCompatActivity {

    int courseID;
    String title;
    String start;
    String end;
    String instructorN;
    String statusString;

    TextView courseTitle;
    TextView courseStart;
    TextView courseEnd;
    TextView instructorName;
    Repository repository = new Repository(getApplication());
    int termID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        courseID = getIntent().getIntExtra("id", -1);
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        instructorN = getIntent().getStringExtra("instructorName");
        termID = getIntent().getIntExtra("termID", -1);

        courseTitle = findViewById(R.id.course_name_field);
        courseTitle.setText(getIntent().getStringExtra(("title")));

        courseStart = findViewById(R.id.editTextStartDateSelected);
        courseStart.setText(start);

        courseEnd = findViewById(R.id.editTextEndDateSelected);
        courseEnd.setText(end);



        instructorName = findViewById(R.id.instructorNameField);
        instructorName.setText(instructorN);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.courseStatus, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner status = findViewById(R.id.statusSpinner);
        status.setAdapter(adapter);

        statusString = status.getSelectedItem().toString();

        Button button = findViewById(R.id.button_save_course);

        button.setOnClickListener(view -> {
            Course course;
            if (courseID == -1) {
                if (repository.getAllCourses().isEmpty())
                    courseID = 1;
                else
                    courseID = getLatestID();

                course = new Course(courseID, courseTitle.getText().toString(),
                                    start, end, "", instructorN, termID);
                repository.insert(course);
            }

            else {
                course = new Course(courseID, courseTitle.getText().toString(),
                                    start, end, "", instructorN, termID);
                repository.update(course);
            }

            Intent intent = new Intent(this, TermDetails.class);
            intent.putExtra("termID", course.getTermID());
            startActivity(intent);
        });
    }
    public int getLatestID() {
        return repository.getAllCourses().get(repository.getAllCourses().size()
                - 1).getPrimary_id() + 1;
    }

    public void deleteCourse(View view) {
        for (Course course : repository.getAllCourses()) {
            if (course.getPrimary_id() == courseID) {
                repository.delete(course);
            }
        }
        Intent intent = new Intent(this, TermDetails.class);
        intent.putExtra("termID", termID);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            super.onBackPressed();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}