package net.decenternet.technicalexam.ui.tasks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.decenternet.technicalexam.R;
import net.decenternet.technicalexam.domain.Task;

import java.util.List;


public class TasksAdapter extends
        RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private List<Task> taskList;
    private static RecyclerOnClickListener taskClickListener;

    public void setItems(List<Task> tasks) {
        this.taskList = tasks;
    }

    public void setOnItemClickListener(RecyclerOnClickListener clickListener) {
        this.taskClickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView taskDescription;
        public CheckBox isTaskCompleted;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            taskDescription = itemView.findViewById(R.id.task_name);
            isTaskCompleted = itemView.findViewById(R.id.cb_task_completed);
        }


        @Override
        public void onClick(View view) {
            taskClickListener.onTaskClick(taskList.get(getAdapterPosition()));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_task, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TasksAdapter.ViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.isTaskCompleted.setChecked(task.getIsCompleted());
        holder.taskDescription.setText(task.getName());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


}