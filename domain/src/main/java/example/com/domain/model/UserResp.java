package example.com.domain.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserResp extends BaseResponse{
    private List<User> userList;
}
