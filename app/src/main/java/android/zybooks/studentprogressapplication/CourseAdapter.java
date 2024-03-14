/*
package android.zybooks.studentprogressapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private List<Course> mCourses;
    private final Context context;
    private LayoutInflater mInflater;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        //private final TextView courseItemView;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            //courseItemView = itemView.findViewById(R.id.courseTextView);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                Course current = mCourses.get(position);
                */
/*Intent intent = new Intent(context, CourseDetails.class);
                intent.putExtra("id", current.getID());
                intent.putExtra("title", current.getTitle());
                intent.putExtra("start", current.getStart());
                intent.putExtra("end", current.getEnd());
                intent.putExtra("status", current.getStatus());
                intent.putExtra("instructorName", current.getInstructor().getName());
                context.startActivity(intent);*//*

            });
        }
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if (mCourses != null) {
            Course current = mCourses.get(position);
            String title = current.getTitle();
            holder.courseItemView.setText(title);
        }
        else {
            holder.courseItemView.setText("No Courses");
        }
    }

    public void setCourses(List<Course> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() { return mCourses.size(); }
}
*/
