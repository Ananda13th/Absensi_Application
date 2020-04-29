package example.com.domain.model;

import lombok.Data;

@Data
public class ResetPasswordReq{
    private String userId;
    private String password;
}
