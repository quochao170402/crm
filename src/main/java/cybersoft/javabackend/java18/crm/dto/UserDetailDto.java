package cybersoft.javabackend.java18.crm.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDetailDto {
    List<TaskDto> taskDtos;
    private int id;
    private String email;
    private String fullname;
    private String avatar;
}
