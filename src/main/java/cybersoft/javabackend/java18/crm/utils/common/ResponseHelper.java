package cybersoft.javabackend.java18.crm.utils.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResponseHelper {
    public static ResponseModel toResponseModel(Object obj, int status) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setBody(obj);
        responseModel.setTime(
                LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))
        );
        responseModel.setStatus(status);
        return responseModel;
    }

    public static ResponseModel toResponseModel(Object obj, boolean isSuccess, String message, int status) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setBody(obj);
        responseModel.setSuccess(isSuccess);
        responseModel.setMessage(message);
        responseModel.setTime(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
        responseModel.setStatus(status);
        return responseModel;
    }
}
