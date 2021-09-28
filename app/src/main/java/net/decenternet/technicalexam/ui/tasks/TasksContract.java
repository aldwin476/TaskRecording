package net.decenternet.technicalexam.ui.tasks;
import net.decenternet.technicalexam.domain.Task;
import java.util.List;

public interface TasksContract {

    interface View {

        void displayTasks(List<Task> tasks);

    }

    interface Presenter {
        void getTasks();

        void onAddTaskClicked(String taskName);

        void onSaveTaskClicked(Task task);

        void onTaskChecked(int taskId);

        void onTaskUnchecked(int taskId);

        void onDeleteTaskClicked(int taskId);

    }

}
