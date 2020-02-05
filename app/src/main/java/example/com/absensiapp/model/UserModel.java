package example.com.absensiapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
public class UserModel extends BaseResponseModel{
    private String userId;
    private String password;
    private String name;
    private String role;
}
