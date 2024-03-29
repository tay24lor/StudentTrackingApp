package android.zybooks.studentprogressapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.zybooks.studentprogressapplication.Course;
import android.zybooks.studentprogressapplication.Database.Repository;
import android.zybooks.studentprogressapplication.R;
import android.zybooks.studentprogressapplication.UI.CourseDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class CreateNoteActivity extends AppCompatActivity {
    Course current;
    String currentNotes;

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

        currentNotes = note.getText().toString();
        Repository repository = new Repository(getApplication());

        for (Course course : repository.getAllCourses()) {
            if (courseID == course.getPrimary_id()) {
                current = course;
            }
        }
        if (courseID != -1) {
            myToolbar.setTitle("Notes for " + current.getTitle());
            note.setText(current.getNotes());
        }
        else
            myToolbar.setTitle("Notes for unnamed course.");
        Button button = findViewById(R.id.save_note_button);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, CourseDetails.class);
            intent.putExtra("courseID", courseID);
            current.setNotes(note.getText().toString());
            repository.update(current);
            startActivity(intent);
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        else if (item.getItemId() == R.id.share_notes_action) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, current.getNotes());
            startActivity(Intent.createChooser(intent, "Share using"));
        }
        return super.onOptionsItemSelected(item);
    }
}