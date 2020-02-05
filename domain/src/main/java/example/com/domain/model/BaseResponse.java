package example.com.domain.model;

import lombok.Data;

@Data
public class BaseResponse{
    private String errorCode;
    private String errorMessage;
}
