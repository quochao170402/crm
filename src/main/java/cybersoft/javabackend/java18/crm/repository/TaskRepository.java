package cybersoft.javabackend.java18.crm.repository;

import cybersoft.javabackend.java18.crm.model.TaskModel;

import java.util.List;

public interface TaskRepository {
    boolean deleteById(int id);

    boolean update(TaskModel taskModel);

    boolean insert(TaskModel taskModel);

    List<TaskModel> findAll(int pageSize, int currentPage);

    TaskModel findById(int id);

    List<TaskModel> findByUserId(int id);

    List<TaskModel> findAllByProject(int id);
}
