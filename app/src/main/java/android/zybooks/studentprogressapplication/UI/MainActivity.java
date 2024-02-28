package android.zybooks.studentprogressapplication.UI;

import android.os.Bundle;
import android.view.Menu;
import android.zybooks.studentprogressapplication.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);*/
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }*/
}