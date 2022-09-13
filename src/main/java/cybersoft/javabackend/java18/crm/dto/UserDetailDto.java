package cybersoft.javabackend.java18.crm.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDetailDto {
    private int id;
    private String email;
    private String fullname;
    private String avatar;
    private List<TaskDto> taskDtos;
}
