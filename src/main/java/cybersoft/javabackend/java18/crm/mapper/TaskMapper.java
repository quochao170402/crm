package cybersoft.javabackend.java18.crm.mapper;

import cybersoft.javabackend.java18.crm.dto.TaskDto;
import cybersoft.javabackend.java18.crm.model.TaskModel;
import cybersoft.javabackend.java18.crm.service.ProjectService;
import cybersoft.javabackend.java18.crm.service.StatusService;
import cybersoft.javabackend.java18.crm.service.UserService;
import cybersoft.javabackend.java18.crm.service.impl.ProjectServiceImpl;
import cybersoft.javabackend.java18.crm.service.impl.StatusServiceImpl;
import cybersoft.javabackend.java18.crm.service.impl.UserServiceImpl;

public class TaskMapper {

    private static TaskMapper instance = null;
    private final UserService userService;
    private final ProjectService projectService;
    private final StatusService statusService;

    private TaskMapper() {
        userService = UserServiceImpl.getService();
        projectService = ProjectServiceImpl.getService();
        statusService = StatusServiceImpl.getService();
    }

    public static TaskMapper getInstance() {
        if (instance == null) instance = new TaskMapper();
        return instance;
    }

    public TaskDto toDto(TaskModel model) {
        if (model == null) return null;
        TaskDto dto = new TaskDto();
        dto.setName(model.getName());
        dto.setStartDate(model.getStartDate());
        dto.setEndDate(model.getEndDate());
        dto.setUser(userService.findById(model.getUserId()).getFullname());
        dto.setProject(projectService.findById(model.getProjectId()).getName());
        dto.setStatus(statusService.findById(model.getStatusId()).getName());
        return dto;
    }
}
