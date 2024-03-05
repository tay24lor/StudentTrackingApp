package android.zybooks.studentprogressapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.zybooks.studentprogressapplication.R;
import android.zybooks.studentprogressapplication.Term;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<Term> terms = new ArrayList<>();

    public void toast() {
        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.add_term);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddTermActivity.class);
            startActivity(intent);
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.termListLayout);

        if (fragment == null) {
            fragment = new ListFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.termListLayout, fragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {

        super.onResume();

    }

    private void generateTermsList(List<Term> terms) {
        LinearLayout layout = findViewById(R.id.termListLayout);
        for (Term term : terms) {
            TextView textView = new TextView(this);
            textView.setText(term.getTitle());
            textView.setTextSize(32);
            textView.setPadding(30,5,0,10);

            layout.addView(textView);
        }
    }

}