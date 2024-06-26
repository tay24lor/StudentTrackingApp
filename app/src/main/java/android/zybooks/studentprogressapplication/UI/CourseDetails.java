package android.zybooks.studentprogressapplication.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.zybooks.studentprogressapplication.Assessment;
import android.zybooks.studentprogressapplication.AssessmentAdapter;
import android.zybooks.studentprogressapplication.Course;
import android.zybooks.studentprogressapplication.Database.Repository;
import android.zybooks.studentprogressapplication.MyReceiver;
import android.zybooks.studentprogressapplication.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class CourseDetails extends AppCompatActivity {

    int id = 1;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";
    int courseID;
    String title;
    String start;
    String end;
    String instructorN;
    String instructorP;
    String instructorE;
    String statusString;
    String note;

    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    Calendar myCalendarStart = Calendar.getInstance();
    Calendar myCalendarEnd = Calendar.getInstance();
    String format = "yyyy-MM-dd";
    SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
    EditText courseTitle;
    EditText courseStart;
    EditText courseEnd;
    EditText instructorName;
    EditText instructorPhone;
    EditText instructorEmail;
    int termID;
    Repository repository;
    Course course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        repository = new Repository(getApplication());

        courseID = getIntent().getIntExtra("courseID", -1);

        courseTitle = findViewById(R.id.course_name_field);
        courseStart = findViewById(R.id.editTextStartDateSelected);
        courseEnd = findViewById(R.id.editTextEndDateSelected);
        instructorName = findViewById(R.id.instructorNameField);
        instructorPhone = findViewById(R.id.instructorPhoneField);
        instructorEmail = findViewById(R.id.instructorEmailField);

        if (courseID != -1)
            populateFields(courseID);
        else
            termID = getIntent().getIntExtra("termID", -1);

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
            statusString = status.getSelectedItem().toString();
            instructorN = instructorName.getText().toString();
            instructorP = instructorPhone.getText().toString();
            instructorE = instructorEmail.getText().toString();
            title = courseTitle.getText().toString();
            start = courseStart.getText().toString();
            end = courseEnd.getText().toString();

            if (courseID == -1) {
                if (repository.getAllCourses().isEmpty())
                    courseID = 1;
                else
                    courseID = getLatestID();

                course = new Course(courseID, title,
                                    start, end, statusString, instructorN, instructorP, instructorE, termID, note);
                repository.insert(course);
            }

            else {
                course = new Course(courseID, courseTitle.getText().toString(),
                                    start, end, statusString, instructorN, instructorP, instructorE, termID, note);
                repository.update(course);
            }

            Intent intent = new Intent(this, TermDetails.class);
            intent.putExtra("termID", termID);
            startActivity(intent);
        });

        List<Assessment> associatedAssessments = new ArrayList<>();
        for (Assessment assessment : repository.getAllAssessments()) {
            if (assessment.getCourseID() == -1) {
                assessment.setCourseID(courseID);
                associatedAssessments.add(assessment);
                repository.update(assessment);
            }
            else if (assessment.getCourseID() == courseID) {
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

    private void populateFields(int courseID) {
        if (!repository.getAllCourses().isEmpty()) {
            for (Course course : repository.getAllCourses()) {
                if (course.getPrimary_id() == courseID) {
                    this.course = course;
                }
            }
        }

        courseTitle.setText(course.getTitle());
        courseStart.setText(course.getStart());
        courseEnd.setText(course.getEnd());
        instructorName.setText(course.getInstructorName());
        statusString = course.getStatus();
        termID = course.getTermID();
        note = course.getNotes();
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (note == null || note.isEmpty()) {
            menu.findItem(R.id.add_notes_action).setTitle("Add Notes");
        }
        else {
            menu.findItem(R.id.add_notes_action).setTitle("View Notes");
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_details_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, TermDetails.class);
            intent.putExtra("termID", termID);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.add_notes_action) {
            Intent intent = new Intent(this, CreateNoteActivity.class);
            intent.putExtra("courseID", courseID);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.set_start_alarm) {
            Calendar cal = Calendar.getInstance();
            Calendar myCal = Calendar.getInstance();
            myCal.setTime(Date.valueOf(courseStart.getText().toString()));
            cal.setTimeInMillis(System.currentTimeMillis());

            Toast.makeText(this, myCal.getTime() + "\n" + cal.getTime(), Toast.LENGTH_LONG).show();
            scheduleNotification(getNotification(myCal.getTime().toString()), myCal.getTimeInMillis());

        }
        else if (item.getItemId() == R.id.set_end_alarm) {
            java.util.Date date = myCalendarEnd.getTime();
            Toast.makeText(this, "Alarm Set!", Toast.LENGTH_SHORT).show();
            scheduleNotification(getNotification("ending"), myCalendarEnd.get(Calendar.DATE));
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
    public void addAssessment(View view) {
        Intent intent = new Intent(this, AssessmentDetails.class);
        intent.putExtra("courseID", courseID);
        startActivity(intent);
    }
    private void scheduleNotification(Notification notification, long delay) {
        /*Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());

        cal.setTimeInMillis(delay);

        if (cal.getTimeInMillis() < System.currentTimeMillis()) {
            cal.setTimeInMillis(System.currentTimeMillis() + 10000);
        }*/
        Intent notificationIntent = new Intent(this, MyReceiver.class);
        notificationIntent.putExtra(MyReceiver.NOTIFICATION_ID, id);
        id++;
        notificationIntent.putExtra(MyReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.set(AlarmManager.RTC_WAKEUP, delay, pendingIntent);
    }
    private Notification getNotification(String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, default_notification_channel_id);
        builder.setContentTitle(courseTitle.getText().toString());
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_logo_foreground);
        builder.setAutoCancel(true);
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        return builder.build();
    }
}