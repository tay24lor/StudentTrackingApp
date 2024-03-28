package android.zybooks.studentprogressapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;
import android.zybooks.studentprogressapplication.UI.AssessmentDetails;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;
        private final TextView assessmentTitleView;

        public AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.assessmentTextView);
            assessmentTitleView = itemView.findViewById(R.id.assessmentTitleTextView);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                Assessment current = mAssessments.get(position);

                Intent intent = new Intent(context, AssessmentDetails.class);
                intent.putExtra("assessmentID",current.getAssessmentID());
                context.startActivity(intent);
            });
        }
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if (mAssessments != null) {
            Assessment current = mAssessments.get(position);
            holder.assessmentTitleView.setText(String.format("%s -- %s", current.getTitle(), current.getType()));
            holder.assessmentItemView.setText(String.format("Start Date: %10s\nEnd Date: %12s", current.getStart(), current.getEnd()));
        }
        else {
            holder.assessmentTitleView.setText("No Assessments");
        }
    }

    public void setAssessments(List<Assessment> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() { return mAssessments.size(); }
}
