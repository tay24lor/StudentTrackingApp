package android.zybooks.studentprogressapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.zybooks.studentprogressapplication.Database.Repository;
import android.zybooks.studentprogressapplication.R;
import android.zybooks.studentprogressapplication.Term;
import android.zybooks.studentprogressapplication.TermAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TermListActivity extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        Button addTermButton = findViewById(R.id.add_term_button);
        addTermButton.setOnClickListener(v -> {
            Intent intent = new Intent(TermListActivity.this, TermDetails.class);
            startActivity(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.term_recycler);
        repository = new Repository(getApplication());
        List<Term> allTerms = repository.getAllTerms();
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);
        StringBuilder names = new StringBuilder();

        for (int i = 0; i < allTerms.size(); i++) {
            names.append(allTerms.get(i).getTitle());
        }
        Toast.makeText(TermListActivity.this, names.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Term> allTerms = repository.getAllTerms();
        RecyclerView recyclerView = findViewById(R.id.term_recycler);
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);
    }

}