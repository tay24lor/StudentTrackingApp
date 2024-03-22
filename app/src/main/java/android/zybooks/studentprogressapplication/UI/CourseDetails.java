package android.zybooks.studentprogressapplication.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.zybooks.studentprogressapplication.Assessment;
import android.zybooks.studentprogressapplication.AssessmentAdapter;
import android.zybooks.studentprogressapplication.Course;
import android.zybooks.studentprogressapplication.CourseAdapter;
import android.zybooks.studentprogressapplication.Database.Repository;
import android.zybooks.studentprogressapplication.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {

    int courseID;
    String start;
    String end;
    String instructorN;
    String statusString;
    int termID;

    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    Calendar myCalendarStart = Calendar.getInstance();
    Calendar myCalendarEnd = Calendar.getInstance();
    String format = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

    EditText courseTitle;
    EditText courseStart;
    EditText courseEnd;
    EditText instructorName;
    Repository repository;
    int tempTermID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        repository = new Repository(getApplication());

        courseID = getIntent().getIntExtra("id", -1);
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        instructorN = getIntent().getStringExtra("instructorName");
        statusString = getIntent().getStringExtra("status");
        termID = getIntent().getIntExtra("termID", -1);

        courseTitle = findViewById(R.id.course_name_field);
        courseTitle.setText(getIntent().getStringExtra(("title")));

        courseStart = findViewById(R.id.editTextStartDateSelected);
        courseStart.setText(start);

        courseEnd = findViewById(R.id.editTextEndDateSelected);
        courseEnd.setText(end);

        instructorName = findViewById(R.id.instructorNameField);
        instructorName.setText(instructorN);

        startDate = (datePicker, year, monthOfYear, dayOfMonth) -> {
            myCalendarStart.set(Calendar.YEAR, year);
            myCalendarStart.set(Calendar.MONTH, monthOfYear);
            myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateStart();
        };

        endDate = (datePicker, year, monthOfYear, dayOfMonth) -> {
            myCalendarEnd.set(Calendar.YEAR, year);
            myCalendarEnd.set(Calendar.MONTH, monthOfYear);
            myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateEnd();
        };

        ImageButton startDateButton = findViewById(R.id.startDatePicker);
        startDateButton.setOnClickListener(view -> {
            String date = courseStart.getText().toString();
            if (date.isEmpty()) date = LocalDate.now().toString();
            myCalendarStart.setTime(Date.valueOf(date));

            new DatePickerDialog(CourseDetails.this, startDate, myCalendarStart.get(Calendar.YEAR),
                    myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();

        });

        ImageButton endDateButton = findViewById(R.id.endDatePicker);
        endDateButton.setOnClickListener(view -> {
            String date = courseEnd.getText().toString();
            if (date.isEmpty()) date = LocalDate.now().toString();
            myCalendarEnd.setTime(Date.valueOf(date));

            new DatePickerDialog(CourseDetails.this, endDate, myCalendarEnd.get(Calendar.YEAR),
                    myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();

        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.courseStatus, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner status = findViewById(R.id.statusSpinner);
        status.setAdapter(adapter);

        if (statusString != null) {
            int spinnerPosition = adapter.getPosition(statusString);
            status.setSelection(spinnerPosition);
        }

        Button button = findViewById(R.id.button_save_course);

        button.setOnClickListener(view -> {
            Course course;
            statusString = status.getSelectedItem().toString();
            instructorN = instructorName.getText().toString();

            if (courseID == -1) {
                if (repository.getAllCourses().isEmpty())
                    courseID = 1;
                else
                    courseID = getLatestID();

                course = new Course(courseID, courseTitle.getText().toString(),
                                    start, end, statusString, instructorN, termID);
                repository.insert(course);
            }

            else {
                course = new Course(courseID, courseTitle.getText().toString(),
                                    start, end, statusString, instructorN, termID);
                repository.update(course);
            }

            Intent intent = new Intent(this, TermDetails.class);
            intent.putExtra("termID", course.getTermID());
            startActivity(intent);
        });

        List<Assessment> associatedAssessments = new ArrayList<>();
        for (Assessment assessment : repository.getAllAssessments()) {
            if (assessment.getCourseID() == courseID) {
                associatedAssessments.add(assessment);
            }
        }

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        RecyclerView assessmentRecyclerView = findViewById(R.id.assessmentRecyclerView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        assessmentRecyclerView.setAdapter(assessmentAdapter);
        assessmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentRecyclerView.addItemDecoration(itemDecoration);
        assessmentAdapter.setAssessments(associatedAssessments);
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
    private void updateStart() {
        courseStart.setText(sdf.format(myCalendarStart.getTime()));
        start = courseStart.getText().toString();
    }
    private void updateEnd() {
        courseEnd.setText(sdf.format(myCalendarEnd.getTime()));
        end = courseEnd.getText().toString();
    }
}