package cybersoft.javabackend.java18.crm.mapper;

import cybersoft.javabackend.java18.crm.dto.ProjectDetailDto;
import cybersoft.javabackend.java18.crm.dto.TaskDto;
import cybersoft.javabackend.java18.crm.model.ProjectModel;
import cybersoft.javabackend.java18.crm.model.TaskModel;
import cybersoft.javabackend.java18.crm.service.TaskService;
import cybersoft.javabackend.java18.crm.service.impl.TaskServiceImpl;

import java.util.List;

public class ProjectMapper {
    private static ProjectMapper instance = null;
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    private ProjectMapper() {
        taskService = TaskServiceImpl.getService();
        taskMapper = TaskMapper.getInstance();
    }

    public static ProjectMapper getInstance() {
        if (instance == null) instance = new ProjectMapper();
        return instance;
    }

    public ProjectDetailDto toProjectDetailDto(ProjectModel model) {
        List<TaskDto> taskDtos = getTaskDtoByProject(model.getId());
        ProjectDetailDto dto = new ProjectDetailDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setTasks(taskDtos);
        return dto;
    }

    private List<TaskDto> getTaskDtoByProject(int id) {
        List<TaskModel> taskModels = taskService.findAllByProject(id);
        return taskModels.stream().map(taskMapper::toDto).toList();
    }
}
