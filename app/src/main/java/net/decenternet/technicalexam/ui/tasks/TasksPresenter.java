package net.decenternet.technicalexam.ui.tasks;

import android.content.SharedPreferences;
import net.decenternet.technicalexam.data.TaskLocalServiceProvider;
import net.decenternet.technicalexam.domain.Task;

public class TasksPresenter implements TasksContract.Presenter {

    private final TasksContract.View view;
    TaskLocalServiceProvider serviceProvider;

    public TasksPresenter(TasksContract.View view, SharedPreferences sharedPreferences) {
        serviceProvider = new TaskLocalServiceProvider(sharedPreferences);
        this.view = view;
    }

    @Override
    public void getTasks() {
        view.displayTasks(serviceProvider.findAll());
    }

    @Override
    public void onAddTaskClicked(String taskName) {
        Task task = new Task();
        task.setId(0);
        task.setIsCompleted(false);
        task.setDescription("");
        task.setName(taskName);
        serviceProvider.save(task);
    }

    @Override
    public void onSaveTaskClicked(Task task) {
        serviceProvider.update(task);
    }

    @Override
    public void onTaskChecked(int taskId) {
        serviceProvider.setChecked(taskId);

    }

    @Override
    public void onTaskUnchecked(int taskId) {
        serviceProvider.setUnChecked(taskId);
    }

    @Override
    public void onDeleteTaskClicked(int taskId) {
        serviceProvider.delete(taskId);

    }
}
