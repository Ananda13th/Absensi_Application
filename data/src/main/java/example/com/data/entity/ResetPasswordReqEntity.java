package example.com.data.entity;

import lombok.Data;

@Data
public class ResetPasswordReqEntity {
    private String userId;
    private String password;
}
