package android.zybooks.studentprogressapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.zybooks.studentprogressapplication.R;
import android.zybooks.studentprogressapplication.Term;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddTermActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);


        Button saveTermButton = findViewById(R.id.save_term_button);
        EditText termTitle = findViewById(R.id.termTitle);
        EditText termStart = findViewById(R.id.startDate);
        EditText termEnd = findViewById(R.id.endDate);

        saveTermButton.setOnClickListener(view -> {
            Term term = new Term(null, null, "", null);

            term.setTitle(termTitle.getText().toString());
            term.setStart(termStart.getText().toString());
            term.setEnd(termEnd.getText().toString());

            Toast.makeText(this, term.getTitle() + "\n" + term.getStart() + "\n" + term.getEnd(), Toast.LENGTH_SHORT).show();

            MainActivity.terms.add(term);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        });
    }
}