package android.zybooks.studentprogressapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.zybooks.studentprogressapplication.UI.TermDetails;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;
        private final TextView termTitleView;




        public TermViewHolder(@NonNull View itemView) {
            super(itemView);

            termItemView = itemView.findViewById(R.id.termTextView);
            termTitleView = itemView.findViewById(R.id.termTitleTextView);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                Term current = mTerms.get(position);
                Intent intent = new Intent(context, TermDetails.class);
                intent.putExtra("termID", current.getPrimary_id());
                intent.putExtra("title", current.getTitle());
                intent.putExtra("start", current.getStart());
                intent.putExtra("end", current.getEnd());
                context.startActivity(intent);
            });
        }
    }


    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }
    
    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if (mTerms != null) {
            Term current = mTerms.get(position);
            holder.termTitleView.setText(current.getTitle());
            holder.termItemView.setText(current.getStart() + " - " + current.getEnd());
        }
        else {
            holder.termItemView.setText("No Terms.");
        }
    }

    public void setTerms(List<Term> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }
}
