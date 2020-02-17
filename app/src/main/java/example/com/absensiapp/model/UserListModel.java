package example.com.absensiapp.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
public class UserListModel extends BaseResponseModel{
    private List<UserModel> userList;
}
