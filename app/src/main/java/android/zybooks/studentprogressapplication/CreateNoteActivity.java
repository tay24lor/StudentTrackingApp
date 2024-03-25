package android.zybooks.studentprogressapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.zybooks.studentprogressapplication.Database.Repository;
import android.zybooks.studentprogressapplication.UI.CourseDetails;

import androidx.appcompat.app.AppCompatActivity;

public class CreateNoteActivity extends AppCompatActivity {
    Course current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);



        int courseID = getIntent().getIntExtra("courseID", -1);
        TextView title = findViewById(R.id.note_title);
        EditText note = findViewById(R.id.note_field);


        Repository repository = new Repository(getApplication());

        for (Course course : repository.getAllCourses()) {
            if (courseID == course.getPrimary_id()) {
                current = course;
            }
        }
        if (courseID != -1) {
            note.setText(current.getNotes());
            title.setText("Notes for " + current.getTitle());
        }

        Button button = findViewById(R.id.save_note_button);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, CourseDetails.class);
            current.setNotes(note.getText().toString());
            startActivity(intent);
        });
    }
}