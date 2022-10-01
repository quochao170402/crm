package cybersoft.javabackend.java18.crm.dto;

import lombok.Data;

@Data
public class TaskDto {
    private int id;
    private String name;
    private String startDate;
    private String endDate;
    private int userId;
    private String userName;
    private int projectId;
    private String projectName;
    private int statusId;
    private String statusName;
}
