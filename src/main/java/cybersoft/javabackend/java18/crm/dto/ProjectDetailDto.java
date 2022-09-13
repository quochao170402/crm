package cybersoft.javabackend.java18.crm.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDetailDto {
    private int id;
    private String name;
    private List<TaskDto> tasks;
}
