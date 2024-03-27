package android.zybooks.studentprogressapplication.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.zybooks.studentprogressapplication.Assessment;
import android.zybooks.studentprogressapplication.Database.Repository;
import android.zybooks.studentprogressapplication.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AssessmentDetails extends AppCompatActivity {
    int assessmentID;
    String start;
    String end;
    String assessmentType;
    int courseID;

    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    Calendar myCalendarStart = Calendar.getInstance();
    Calendar myCalendarEnd = Calendar.getInstance();
    String format = "yyyy-MM-dd";
    SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

    EditText assessmentTitle;
    EditText assessmentStart;
    EditText assessmentEnd;
    Repository repository;
    Assessment assessment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        repository = new Repository(getApplication());

        assessmentID = getIntent().getIntExtra("id", -1);

        assessmentTitle = findViewById(R.id.assessment_name_field);
        assessmentStart = findViewById(R.id.editTextAssessmentStartDateSelected);
        assessmentEnd = findViewById(R.id.editTextAssessmentEndDateSelected);

        courseID = getIntent().getIntExtra("courseID", -1);

        if (assessmentID != -1)
            populateFields(assessmentID);
        else
            courseID = getIntent().getIntExtra("courseID", -1);

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

        ImageButton startDateButton = findViewById(R.id.assessmentStartDatePicker);
        startDateButton.setOnClickListener(view -> {
            String date = assessmentStart.getText().toString();

            if (date.isEmpty()) date = LocalDate.now().toString();
            myCalendarStart.setTime(Date.valueOf(date));

            new DatePickerDialog(AssessmentDetails.this, startDate, myCalendarStart.get(Calendar.YEAR),
                    myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();

        });

        ImageButton endDateButton = findViewById(R.id.assessmentEndDatePicker);
        endDateButton.setOnClickListener(view -> {
            String date = assessmentEnd.getText().toString();
            if (date.isEmpty()) date = LocalDate.now().toString();
            myCalendarEnd.setTime(Date.valueOf(date));

            new DatePickerDialog(AssessmentDetails.this, endDate, myCalendarEnd.get(Calendar.YEAR),
                    myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();

        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.assessmentType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner type = findViewById(R.id.typeSpinner);
        type.setAdapter(adapter);

        if (assessmentType != null) {
            int spinnerPosition = adapter.getPosition(assessmentType);
            type.setSelection(spinnerPosition);
        }

        Button button = findViewById(R.id.button_save_assessment);
        button.setOnClickListener(view -> {
            assessmentType = type.getSelectedItem().toString();

            if (assessmentID == -1) {
                if (repository.getAllAssessments().isEmpty())
                    assessmentID = 1;
                else
                    assessmentID = getLatestID();

                assessment = new Assessment(assessmentID, assessmentTitle.getText().toString(), assessmentType, start,
                                            end, courseID);
                repository.insert(assessment);
            }
            else {
                assessment = new Assessment(assessmentID, assessmentTitle.getText().toString(), assessmentType, start,
                        end, courseID);
                repository.update(assessment);
            }
            Intent intent = new Intent(this, CourseDetails.class);
            intent.putExtra("courseID", courseID);
            startActivity(intent);
        });
    }

    private void populateFields(int assessmentID) {
        if (!repository.getAllAssessments().isEmpty()) {
            for (Assessment assessment : repository.getAllAssessments()) {
                if (assessment.getAssessmentID() == assessmentID) {
                    this.assessment = assessment;
                }
            }
        }
        assessmentTitle.setText(assessment.getTitle());
        assessmentStart.setText(assessment.getStart());
        assessmentEnd.setText(assessment.getEnd());
        assessmentType = assessment.getType();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_details_menu, menu);
        return true;
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
        assessmentStart.setText(sdf.format(myCalendarStart.getTime()));
        start = assessmentStart.getText().toString();
    }
    private void updateEnd() {
        assessmentEnd.setText(sdf.format(myCalendarEnd.getTime()));
        end = assessmentEnd.getText().toString();
    }
    public int getLatestID() {
        return repository.getAllAssessments().get(repository.getAllAssessments().size()
                - 1).getAssessmentID() + 1;
    }
}