package example.com.absensiapp.model;

import lombok.Data;

@Data
public class ResetPasswordReqModel{
    private String userId;
    private String password;
}
