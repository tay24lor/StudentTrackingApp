package android.zybooks.studentprogressapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.zybooks.studentprogressapplication.Database.Repository;
import android.zybooks.studentprogressapplication.R;
import android.zybooks.studentprogressapplication.Term;
import android.zybooks.studentprogressapplication.TermDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class AddTermActivity extends AppCompatActivity {


    Repository repository;

    int termID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);


        Button saveTermButton = findViewById(R.id.save_term_button);
        EditText termTitle = findViewById(R.id.termTitle);
        EditText termStart = findViewById(R.id.startDate);
        EditText termEnd = findViewById(R.id.endDate);

        saveTermButton.setOnClickListener(view -> {
            Term term = new Term(0, null, null, "");

            term.setTitle(termTitle.getText().toString());
            term.setStart(termStart.getText().toString());
            term.setEnd(termEnd.getText().toString());

            repository = new Repository(getApplication());
            repository.insert(term);

            Intent intent = new Intent(this, TermListActivity.class);
            startActivity(intent);

        });
    }
}