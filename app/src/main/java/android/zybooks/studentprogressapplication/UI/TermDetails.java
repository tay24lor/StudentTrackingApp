package android.zybooks.studentprogressapplication.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.zybooks.studentprogressapplication.Course;
import android.zybooks.studentprogressapplication.CourseAdapter;
import android.zybooks.studentprogressapplication.Database.Repository;
import android.zybooks.studentprogressapplication.R;
import android.zybooks.studentprogressapplication.Term;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import java.util.Objects;

public class TermDetails extends AppCompatActivity {

    Term current;
    int termID;
    String title;
    String start;
    String end;
    String format = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    Calendar myCalendarStart = Calendar.getInstance();
    Calendar myCalendarEnd = Calendar.getInstance();
    String myTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        repository = new Repository(getApplication());



        editTitle = findViewById(R.id.term_name_field);
        editStartDate = findViewById(R.id.editTextStartDateSelected);
        editEndDate = findViewById(R.id.editTextEndDateSelected);

        termID = getIntent().getIntExtra("termID", -1);

        if (termID != -1)
            populateFields(termID);

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
            String date = editStartDate.getText().toString();
            if (date.isEmpty()) date = LocalDate.now().toString();
            myCalendarStart.setTime(Date.valueOf(date));

            new DatePickerDialog(TermDetails.this, startDate, myCalendarStart.get(Calendar.YEAR),
                                 myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();

        });

        ImageButton endDateButton = findViewById(R.id.endDatePicker);
        endDateButton.setOnClickListener(view -> {
            String date = editEndDate.getText().toString();
            if (date.isEmpty()) date = LocalDate.now().toString();
            myCalendarEnd.setTime(Date.valueOf(date));

            new DatePickerDialog(TermDetails.this, endDate, myCalendarEnd.get(Calendar.YEAR),
                    myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();

        });


        Button saveButton = findViewById(R.id.button_save_term);
        saveButton.setOnClickListener(view -> {
            if (termID == -1) {
                if (repository.getAllTerms().isEmpty())
                    termID = 1;
                else
                    termID = getLatestID();

                current = new Term(termID, editTitle.getText().toString(), editStartDate.getText().toString(),
                                editEndDate.getText().toString());
                repository.insert(current);
            }
            else {
                current = new Term(termID, editTitle.getText().toString(), editStartDate.getText().toString(),
                                editEndDate.getText().toString());
                repository.update(current);
            }

            for (Course course : repository.getAllCourses()) {
                if (course.getTermID() == -1) {
                    course.setTermID(termID);
                }
            }

            Intent intent = new Intent(this, TermListActivity.class);
            startActivity(intent);
        });

        List<Course> associatedCourses = new ArrayList<>();
        for (Course course : repository.getAllCourses()) {
            if (course.getTermID() == -1) {
                course.setTermID(termID);
                associatedCourses.add(course);
                repository.update(course);
            }
            else if (course.getTermID() == termID) {
                associatedCourses.add(course);
            }
        }

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        RecyclerView courseRecyclerView = findViewById(R.id.courseRecyclerView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        courseRecyclerView.setAdapter(courseAdapter);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseRecyclerView.addItemDecoration(itemDecoration);
        courseAdapter.setCourses(associatedCourses);
    }

    private void populateFields(int termID) {
        if (!repository.getAllTerms().isEmpty()) {
            for (Term term : repository.getAllTerms()) {
                if (term.getPrimary_id() == termID) {
                    current = term;
                }
            }
        }
        editTitle.setText(current.getTitle());
        editStartDate.setText(current.getStart());
        editEndDate.setText(current.getEnd());
    }

    /*protected void onResume() {
        super.onResume();
        *//*Term term;
        termID = getIntent().getIntExtra("termID", -1);
        if (!repository.getAllTerms().isEmpty()) {
            term = repository.getAllTerms().get(termID - 1);
            editTitle.setText(term.getTitle());
            editStartDate.setText(term.getStart());
            editEndDate.setText(term.getEnd());
        }*//*
        *//*if (!repository.getAllTerms().isEmpty()) {
            Term term = null;
            for (Term termInList : repository.getAllTerms()) {
                if (termInList.getPrimary_id() == termID) {
                    term = termInList;
                }
            }


            assert term != null;


        }
        *//*
        List<Course> associatedCourses = new ArrayList<>();
        for (Course course : repository.getAllCourses()) {
            if (course.getTermID() == -1) {
                course.setTermID(termID);
                associatedCourses.add(course);
                repository.update(course);
            }
            else if (course.getTermID() == termID) {
                associatedCourses.add(course);
            }
        }

        RecyclerView courseRecyclerView = findViewById(R.id.courseRecyclerView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        courseRecyclerView.setAdapter(courseAdapter);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(associatedCourses);
    }*/

    private void updateStart() {
        editStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }
    private void updateEnd() {
        editEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public int getLatestID() {
        return repository.getAllTerms().get(repository.getAllTerms().size()
               - 1).getPrimary_id() + 1;
    }

    public void deleteTerm(View view) {
        List<Course> associatedCourses = new ArrayList<>();
        for (Course course : repository.getAllCourses()) {
            if (course.getTermID() == termID) {
                associatedCourses.add(course);
            }
        }
        for (Term term : repository.getAllTerms()) {
            if (termID == term.getPrimary_id()) {
                if (associatedCourses.isEmpty()) {
                    repository.delete(term);
                }
                else {
                    Toast.makeText(this, "Term cannot be deleted while courses are assigned to it.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        Intent intent = new Intent(this, TermListActivity.class);
        startActivity(intent);
    }

    public void addCourse(View view) {
        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra("termID", termID);
        startActivity(intent);
    }
}