package android.zybooks.studentprogressapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.zybooks.studentprogressapplication.UI.MainActivity;

import java.util.List;

public class TermListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_term_list, container, false);
        LinearLayout layout = (LinearLayout) view;

        List<Term> termList = MainActivity.terms;
        for (int i = 0; i < termList.size(); i++) {
            Button button = new Button(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(0, 0, 0, 10);
            button.setLayoutParams(layoutParams);

            Term term = termList.get(i + 1);
            button.setText(term.getTitle());
            button.setTag(Integer.toString(term.getId()));

            button.setOnClickListener(buttonClickListener);

            layout.addView(button);
        }
        return view;
    }

    private final View.OnClickListener buttonClickListener = new View.OnClickListener() {

        MainActivity object;
        @Override
        public void onClick(View view) {
            object.toast();
        }
    };
}