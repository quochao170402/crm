package cybersoft.javabackend.java18.crm.utils.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel implements Serializable {
    private Object body;
    private String message;
    private boolean isSuccess;
    private String time;
    private int status;
}
