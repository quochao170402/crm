package cybersoft.javabackend.java18.crm.service;

import cybersoft.javabackend.java18.crm.model.TaskModel;

import java.util.List;

public interface TaskService {
    TaskModel findById(int id);

    List<TaskModel> findAll(int pageSize, int currentPage);

    boolean insert(TaskModel taskModel);

    boolean update(TaskModel taskModel);

    boolean deleteById(int id);

    List<TaskModel> findByUserId(int id);

    List<TaskModel> findAllByProject(int id);
}
