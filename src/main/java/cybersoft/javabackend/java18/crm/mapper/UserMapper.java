package cybersoft.javabackend.java18.crm.mapper;

import cybersoft.javabackend.java18.crm.dto.TaskDto;
import cybersoft.javabackend.java18.crm.dto.UserDetailDto;
import cybersoft.javabackend.java18.crm.dto.UserDto;
import cybersoft.javabackend.java18.crm.model.UserModel;
import cybersoft.javabackend.java18.crm.service.RoleService;
import cybersoft.javabackend.java18.crm.service.TaskService;
import cybersoft.javabackend.java18.crm.service.impl.RoleServiceImpl;
import cybersoft.javabackend.java18.crm.service.impl.TaskServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    private static UserMapper instance = null;
    private final RoleService roleService;
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    private UserMapper() {
        roleService = RoleServiceImpl.getService();
        taskService = TaskServiceImpl.getService();
        taskMapper = TaskMapper.getInstance();
    }

    public static UserMapper getInstance() {
        if (instance == null) instance = new UserMapper();
        return instance;
    }

    public UserDto toDto(UserModel model) {
        if (model == null) return null;
        UserDto dto = new UserDto();
        dto.setId(model.getId());
        dto.setEmail(model.getEmail());
        dto.setFullname(model.getFullname());
        dto.setAvatar(model.getAvatar());
        dto.setRole(roleService.findById(model.getRoleId()).getName());
        return dto;
    }

    public UserDetailDto toUserDetailDto(UserModel model) {
        if (model == null) return null;
        UserDetailDto dto = new UserDetailDto();
        dto.setId(model.getId());
        dto.setEmail(model.getEmail());
        dto.setFullname(model.getFullname());
        dto.setAvatar(model.getAvatar());
        List<TaskDto> taskDtos = new ArrayList<>();
        taskService.findByUserId(model.getId()).forEach(task -> {
            taskDtos.add(taskMapper.toDto(task));
        });
        dto.setTaskDtos(taskDtos);
        return dto;
    }
}
