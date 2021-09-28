package net.decenternet.technicalexam.ui.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.decenternet.technicalexam.R;
import net.decenternet.technicalexam.domain.Task;

import java.util.List;

public class TasksActivity extends AppCompatActivity implements TasksContract.View {
    /**
     * Finish this simple task recording app.
     * The following are the remaining todos for this project:
     * 1. Make sure all defects are fixed.
     * 2. Showing a dialog to add/edit the task.
     * 3. Allowing the user to toggle it as completed.
     * 4. Allowing the user to delete a task.
     */
    private TasksPresenter presenter;
    RecyclerView taskRecycler;
    TasksAdapter adapter;
    FloatingActionButton addBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("default", MODE_PRIVATE);
        presenter = new TasksPresenter(this, pref);
        addBtn = findViewById(R.id.add_task_btn);
        taskRecycler = (RecyclerView) findViewById(R.id.tasks_recycler);
        adapter = new TasksAdapter();
        adapter.setOnItemClickListener(new RecyclerOnClickListener() {
            @Override
            public void onTaskClick(Task task) {

                showEditDialog(task);
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });
        presenter.getTasks();
    }

    public void showEditDialog(final Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TasksActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_edit_task, viewGroup, false);
        builder.setView(dialogView);

        final EditText taskDescription = dialogView.findViewById(R.id.task_description);
        final EditText taskTitle = dialogView.findViewById(R.id.task_title);
        final Button saveBtn = dialogView.findViewById(R.id.save_task);
        final Button cancelBtn = dialogView.findViewById(R.id.cancel_task);
        final CheckBox isCompletedCb = dialogView.findViewById(R.id.isCompleted);
        final ImageView deleteBtn = dialogView.findViewById(R.id.delete_task);

        taskDescription.setText(task.getDescription());
        taskTitle.setText(task.getName());
        isCompletedCb.setChecked(task.getIsCompleted());

        final AlertDialog alertDialog = builder.create();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.setDescription(taskDescription.getText().toString());
                task.setName(taskTitle.getText().toString());
                presenter.onSaveTaskClicked(task);
                presenter.getTasks();
                alertDialog.dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getTasks();
                alertDialog.dismiss();
            }
        });
        isCompletedCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    presenter.onTaskChecked(task.getId());
                } else {
                    presenter.onTaskUnchecked(task.getId());
                }
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDeleteTaskClicked(task.getId());
                presenter.getTasks();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TasksActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_add_edit_task, viewGroup, false);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        final EditText taskDescription = dialogView.findViewById(R.id.task_description_et);
        Button cancelTask = dialogView.findViewById(R.id.cancel_task_btn);
        Button saveTask = dialogView.findViewById(R.id.save_task_btn);
        saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onAddTaskClicked(taskDescription.getText().toString());
                presenter.getTasks();
                alertDialog.dismiss();
            }
        });
        cancelTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void displayTasks(List<Task> tasks) {
        taskRecycler.setAdapter(adapter);
        adapter.setItems(tasks);
        adapter.notifyDataSetChanged();
    }
}
