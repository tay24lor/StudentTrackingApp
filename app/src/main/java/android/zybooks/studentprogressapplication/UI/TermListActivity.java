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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TermListActivity extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        repository = new Repository(getApplication());
        List<Term> allTerms = repository.getAllTerms();

        Button addTermButton = findViewById(R.id.add_term_button);



        addTermButton.setOnClickListener(v -> {
            Intent intent = new Intent(TermListActivity.this, TermDetails.class);
            intent.putExtra("termID", -1);
            startActivity(intent);
        });


        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        RecyclerView recyclerView = findViewById(R.id.term_recycler);
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        termAdapter.setTerms(allTerms);
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