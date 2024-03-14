package android.zybooks.studentprogressapplication.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.zybooks.studentprogressapplication.Course;
import android.zybooks.studentprogressapplication.CourseAdapter;
import android.zybooks.studentprogressapplication.Database.Repository;
import android.zybooks.studentprogressapplication.R;
import android.zybooks.studentprogressapplication.Term;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TermDetails extends AppCompatActivity {

    int termID;
    String title;
    String start;
    String end;

    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    Calendar myCalendarStart = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        repository = new Repository(getApplication());

        editTitle = findViewById(R.id.term_name_field);
        editStartDate = findViewById(R.id.editTextStartDateSelected);
        editEndDate = findViewById(R.id.editTextEndDateSelected);

        termID = getIntent().getIntExtra("id", -1);

        title = getIntent().getStringExtra("title");
        editTitle.setText(title);

        start = getIntent().getStringExtra("start");
        editStartDate.setText(start);

        end = getIntent().getStringExtra("end");
        editEndDate.setText(end);

        String format = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

        ImageButton startDateButton = findViewById(R.id.startDatePicker);
        startDateButton.setOnClickListener(view -> {
            String date = editStartDate.getText().toString();
            if (date.isEmpty()) date = "3/12/2024";
            try {
                myCalendarStart.setTime(Objects.requireNonNull(sdf.parse(date)));

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            new DatePickerDialog(TermDetails.this, startDate, myCalendarStart.get(Calendar.MONTH),
                                 myCalendarStart.get(Calendar.DAY_OF_MONTH), myCalendarStart.get(Calendar.YEAR)).show();

        });

        startDate = (datePicker, month1, day1, year1) -> {
            myCalendarStart.set(Calendar.MONTH, month1);
            myCalendarStart.set(Calendar.DAY_OF_MONTH, day1);
            myCalendarStart.set(Calendar.YEAR, year1);
            updateStart();
        };

        Button saveButton = findViewById(R.id.button_save_term);
        saveButton.setOnClickListener(view -> {
            Term term;
            if (termID == -1) {
                if (repository.getAllTerms().isEmpty())
                    termID = 1;
                else {
                    termID = getLatestID();
                }

                term = new Term(termID, editTitle.getText().toString(), editStartDate.getText().toString(),
                                editEndDate.getText().toString());
                repository.insert(term);
            }
            else {
                term = new Term(termID, editTitle.getText().toString(), editStartDate.getText().toString(),
                        editEndDate.getText().toString());
                repository.update(term);
            }
            Intent intent = new Intent(this, TermListActivity.class);
            startActivity(intent);

        });

        //List<Course> termCourses = repository.getCourses();

        RecyclerView courseRecyclerView = findViewById(R.id.courseRecyclerView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        courseRecyclerView.setAdapter(courseAdapter);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //courseAdapter.setCourses(termCourses);
    }

    private void updateStart() {
        String format = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

        editStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }
    public int getLatestID() {
        return repository.getAllTerms().get(repository.getAllTerms().size()
               - 1).getId() + 1;
    }

    public void deleteTerm(View view) {
        Term term = new Term(termID, editTitle.getText().toString(), editStartDate.getText().toString(),
                editEndDate.getText().toString());
        repository.delete(term);
        Intent intent = new Intent(this, TermListActivity.class);
        startActivity(intent);
    }
}