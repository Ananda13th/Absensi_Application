package example.com.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseResponse{
    private String userId;
    private String password;
    private String name;
    private String role;
}
