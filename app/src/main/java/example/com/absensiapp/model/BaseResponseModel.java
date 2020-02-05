package example.com.absensiapp.model;

import lombok.Data;

@Data
public class BaseResponseModel {
    private String errorCode;
    private String errorMessage;
}
