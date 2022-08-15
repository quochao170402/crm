package cybersoft.javabackend.java18.crm.model;

import lombok.Data;

@Data
public class UserModel {
    private int id;
    private String email;
    private String password;
    private String fullname;
    private String avatar;
    private int roleId;
}
