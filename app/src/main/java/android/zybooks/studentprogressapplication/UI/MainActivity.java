package android.zybooks.studentprogressapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.zybooks.studentprogressapplication.R;

import androidx.appcompat.app.AppCompatActivity;

/*TODO
*  Work on datestamps
*  Work on sending selected term info to term details*/
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.enter_button);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TermListActivity.class);
            startActivity(intent);
        });
    }
}