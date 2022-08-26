package cybersoft.javabackend.java18.crm.model;

import lombok.Data;

@Data
public class TaskModel {
    private int id;
    private String name;
    private String startDate;
    private String endDate;
    private int userId;
    private int projectId;
    private int statusId;
}
