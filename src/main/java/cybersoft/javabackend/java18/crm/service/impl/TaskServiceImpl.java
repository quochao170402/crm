package cybersoft.javabackend.java18.crm.service.impl;

import cybersoft.javabackend.java18.crm.model.TaskModel;
import cybersoft.javabackend.java18.crm.repository.TaskRepository;
import cybersoft.javabackend.java18.crm.repository.impl.TaskRepositoryImpl;
import cybersoft.javabackend.java18.crm.service.TaskService;

import java.util.List;

public class TaskServiceImpl implements TaskService {
    private static TaskService taskService;
    private final TaskRepository taskRepository;

    private TaskServiceImpl() {
        taskRepository = TaskRepositoryImpl.getRepository();
    }

    public static TaskService getService() {
        if (taskService == null) taskService = new TaskServiceImpl();
        return taskService;
    }

    @Override
    public TaskModel findById(int id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<TaskModel> findAll(int pageSize, int currentPage) {
        return taskRepository.findAll(pageSize, currentPage);
    }

    @Override
    public boolean insert(TaskModel taskModel) {
        if (taskModel == null) return false;
        return taskRepository.insert(taskModel);
    }

    @Override
    public boolean update(TaskModel taskModel) {
        if (taskModel == null) return false;
        return taskRepository.update(taskModel);
    }

    @Override
    public boolean deleteById(int id) {
        return taskRepository.deleteById(id);
    }

    @Override
    public List<TaskModel> findByUserId(int id) {
        return taskRepository.findByUserId(id);
    }

    @Override
    public List<TaskModel> findAllByProject(int id) {
        return taskRepository.findAllByProject(id);
    }
}
