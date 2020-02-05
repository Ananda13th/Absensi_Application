package example.com.data.entity.mapper;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import example.com.data.entity.UserEntity;
import example.com.data.entity.UserRespEntity;
import example.com.domain.model.User;
import example.com.domain.model.UserResp;

public class UserEntityMapper extends BaseResponseEntityMapper{

    public UserResp userRespToDomain(UserRespEntity respEntity) {
        UserResp userResp = new UserResp();
        List<User> userList = new ArrayList<>();

        userResp.setErrorCode(respEntity.getErrorCode());
        userResp.setErrorMessage(respEntity.getErrorMessage());
        for(int i=0;i<respEntity.getUserList().size();i++)
        {
            UserEntity userEntity = respEntity.getUserList().get(i);
            User user = new User();

            user.setName(userEntity.getName());
            user.setPassword(userEntity.getPassword());
            user.setUserId(userEntity.getUserId());
            userList.add(user);
        }
        userResp.setUserList(userList);
        return userResp;
    }

    public User userToDomain(UserEntity userEntity) {
        User user = new User();

        user.setName(userEntity.getName());
        user.setPassword(userEntity.getPassword());
        user.setUserId(userEntity.getUserId());
        user.setRole(userEntity.getRole());

        return user;
    }

    public UserEntity userToData(User user) {
        UserEntity userEntity = new UserEntity();

        userEntity.setName(user.getName());
        userEntity.setPassword(user.getPassword());
        userEntity.setUserId(user.getUserId());
        return userEntity;
    }

}
