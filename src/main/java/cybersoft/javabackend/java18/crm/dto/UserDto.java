package cybersoft.javabackend.java18.crm.dto;

import lombok.Data;

@Data
public class UserDto {
    private int id;
    private String email;
    private String fullname;
    private String avatar;
    private String role;
}
