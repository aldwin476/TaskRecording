package net.decenternet.technicalexam.data;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.decenternet.technicalexam.domain.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TaskLocalServiceProvider implements TaskLocalService {

    private final SharedPreferences sharedPreferences;
    private Collection<Task> localTasks;

    public TaskLocalServiceProvider(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        localTasks = new Gson()
                .fromJson(
                        sharedPreferences.getString("tasks", "[]"),
                        new TypeToken<List<Task>>() {
                        }.getType()
                );
    }

    @Override
    public void save(Task task) {
        ArrayList<Task> tasks = new ArrayList<>(localTasks);
        task.setId(getNextId());
        tasks.add(task);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("tasks", new Gson().toJson(tasks));
        editor.apply();

        localTasks = tasks;
    }

    @Override
    public void update(Task task) {
        ArrayList<Task> tasks = new ArrayList<>(localTasks);

        for (int pos = 0; pos < tasks.size(); pos++) {
            if (tasks.get(pos).getId() == task.getId()) {
                tasks.set(pos, task);
            }
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("tasks", new Gson().toJson(tasks));
        editor.apply();

        localTasks = tasks;
    }

    @Override
    public void delete(Integer id) {
        ArrayList<Task> tasks = new ArrayList<>(localTasks);
        for (int pos = 0; pos < tasks.size(); pos++) {
            if (tasks.get(pos).getId().equals(id)) {
                tasks.remove(pos);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("tasks", new Gson().toJson(tasks));
                editor.apply();

                localTasks = tasks;
                return;
            }
        }
    }

    @Override
    public void setChecked(Integer id) {
        ArrayList<Task> tasks = new ArrayList<>(localTasks);
        for (int pos = 0; pos < tasks.size(); pos++) {
            if (tasks.get(pos).getId() == id) {
                tasks.get(pos).setIsCompleted(true);
                update(tasks.get(pos));
                return;
            }
        }
    }

    @Override
    public void setUnChecked(Integer id) {
        ArrayList<Task> tasks = new ArrayList<>(localTasks);
        for (int pos = 0; pos < tasks.size(); pos++) {
            if (tasks.get(pos).getId() == id) {
                tasks.get(pos).setIsCompleted(false);
                update(tasks.get(pos));
                return;
            }
        }
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(localTasks);
    }

    private Integer getNextId() {
        return localTasks.size() + 1;
    }
}
