package android.zybooks.studentprogressapplication.UI;

import android.content.Intent;
import android.os.Bundle;
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
    TextView title;
    Repository repository = new Repository(getApplication());
    Course course;
    int termID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        courseID = getIntent().getIntExtra("id", -1);

        termID = getIntent().getIntExtra("termID", -1);
        title = findViewById(R.id.course_name_field);
        title.setText(getIntent().getStringExtra(("title")));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.courseStatus, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner status = findViewById(R.id.statusSpinner);
        status.setAdapter(adapter);

        Button button = findViewById(R.id.button_save_course);

        button.setOnClickListener(view -> {
            Course course = new Course();
            course.setTermID(termID);
            course.setTitle(title.getText().toString());
            repository.insert(course);
            Intent intent = new Intent(this, TermDetails.class);
            intent.putExtra("termID", termID);
            startActivity(intent);
        });
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
}