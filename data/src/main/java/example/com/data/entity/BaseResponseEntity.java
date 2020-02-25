package example.com.data.entity;

import lombok.Data;

@Data
public class BaseResponseEntity {
    private String errorCode;
    private String errorMessage;
}
