package android.zybooks.studentprogressapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.zybooks.studentprogressapplication.R;
import android.zybooks.studentprogressapplication.Term;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<Term> terms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.add_term);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddTermActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {

        super.onResume();

        if (!terms.isEmpty()) {
            Toast.makeText(this, terms.get(0).getTitle(), Toast.LENGTH_SHORT).show();
        }
    }

    private static class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termView;

        private TermViewHolder(View vTermView) {
            super(vTermView);
            termView = vTermView.findViewById(R.id.textView);
        }

        public void bind(String text) {
            termView.setText(text);
        }

        static TermViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_item, parent, false);
            return new TermViewHolder(view);
        }
    }

    private class TermListAdapter extends ListAdapter<Term, TermViewHolder> {
        public TermListAdapter(DiffUtil.ItemCallback<Term> diffCallback) {
            super(diffCallback);
        }

        @NonNull
        @Override
        public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return TermViewHolder.create(parent);
        }

        @Override
        public void onBindViewHolder(TermViewHolder holder, int position) {
            Term current = getItem(position);
            holder.bind(current.getTitle());
        }
    }
}