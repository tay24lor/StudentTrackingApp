package android.zybooks.studentprogressapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.zybooks.studentprogressapplication.Database.Repository;
import android.zybooks.studentprogressapplication.UI.CourseDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class CreateNoteActivity extends AppCompatActivity {
    Course current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        int courseID = getIntent().getIntExtra("courseID", -1);
        EditText note = findViewById(R.id.note_field);
        String notes = getIntent().getStringExtra("notes");
        if (notes != null)
            note.setText(notes);

        Repository repository = new Repository(getApplication());

        for (Course course : repository.getAllCourses()) {
            if (courseID == course.getPrimary_id()) {
                current = course;
            }
        }
        if (courseID != -1)
            myToolbar.setTitle("Notes for " + current.getTitle());
        else
            myToolbar.setTitle("Notes for unnamed course.");
        Button button = findViewById(R.id.save_note_button);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, CourseDetails.class);
            current.setNotes(note.getText().toString());
            if (courseID == -1) repository.insert(current);
            else repository.update(current);
            intent.putExtra("courseID", courseID);
            intent.putExtra("notes", note.getText().toString());
            startActivity(intent);
        });
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