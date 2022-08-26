package cybersoft.javabackend.java18.crm.dto;

import lombok.Data;

@Data
public class TaskDto {
    private String name;
    private String startDate;
    private String endDate;
    private String user;
    private String project;
    private String status;
}
