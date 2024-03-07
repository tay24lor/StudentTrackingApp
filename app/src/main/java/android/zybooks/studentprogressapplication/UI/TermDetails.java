package android.zybooks.studentprogressapplication.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.zybooks.studentprogressapplication.Database.Repository;
import android.zybooks.studentprogressapplication.R;
import android.zybooks.studentprogressapplication.Term;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class TermDetails extends AppCompatActivity {

    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        repository = new Repository(getApplication());
        editTitle = findViewById(R.id.term_name_field);
        editStartDate = findViewById(R.id.editTextStartDateSelected);
        editEndDate = findViewById(R.id.editTextEndDateSelected);

        Button saveButton = findViewById(R.id.button_save_term);
        saveButton.setOnClickListener(view -> {
            Term term;
            term = new Term(1,
                                editStartDate.getText().toString(),
                                editEndDate.getText().toString(),
                                editTitle.getText().toString());
            repository.insert(term);

            term = new Term(2, editStartDate.getText().toString(), editEndDate.getText().toString(), editTitle.getText().toString());
            repository.insert(term);

        });
    }
}