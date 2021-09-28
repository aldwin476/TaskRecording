package net.decenternet.technicalexam.data;

import net.decenternet.technicalexam.domain.Task;

import java.util.List;

public interface TaskLocalService {

    void save(Task task);
    void update(Task task);
    void delete(Integer taskId);
    void setChecked(Integer id);
    void setUnChecked(Integer id);
    List<Task> findAll();

}
