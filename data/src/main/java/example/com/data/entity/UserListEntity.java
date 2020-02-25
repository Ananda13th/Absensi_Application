package example.com.data.entity;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserListEntity extends BaseResponseEntity{
    private List<UserEntity> userList;
}