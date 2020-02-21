package example.com.data.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class BaseResponseEntity {
    @SerializedName("error_code")
    private String errorCode;
    @SerializedName("error_message")
    private String errorMessage;
}
