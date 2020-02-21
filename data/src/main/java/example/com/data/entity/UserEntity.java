package example.com.data.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserEntity extends BaseResponseEntity{
    @SerializedName("user_id")
    private String userId;
    private String password;
    private String name;
    private String role;
}
