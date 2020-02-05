package example.com.data.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserEntity extends BaseResponseEntity{
    private String userId;
    private String password;
    private String name;
    private String role;
}
