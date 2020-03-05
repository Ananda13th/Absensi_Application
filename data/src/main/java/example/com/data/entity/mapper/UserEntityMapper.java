package example.com.data.entity.mapper;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import example.com.data.entity.CheckInReqEntity;
import example.com.data.entity.UploadImageReqEntity;
import example.com.data.entity.UserEntity;
import example.com.data.entity.UserListEntity;
import example.com.domain.model.CheckInReq;
import example.com.domain.model.UploadImageReq;
import example.com.domain.model.User;
import example.com.domain.model.UserList;

public class UserEntityMapper extends BaseResponseEntityMapper {

    Gson gson = new Gson();

    public UserList userRespToDomain(UserListEntity respEntity) {

        Log.d("TET1", respEntity.toString());
        UserList userResp = new UserList();
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
            user.setRole(userEntity.getRole());
            userList.add(user);
        }
        userResp.setUserList(userList);
        Log.d("TET", userResp.toString());
        return userResp;
    }

//    public UserList userRespToDomain(UserListEntity respEntity) {
//        String userListJson = gson.toJson(respEntity);
//        UserList userList = gson.fromJson(userListJson, UserList.class);
//        return userList;
//    }

    public User userToDomain(UserEntity userEntity) {
        User user = new User();

        user.setErrorCode(userEntity.getErrorCode());
        user.setErrorMessage(userEntity.getErrorMessage());
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


    public CheckInReqEntity userToData(CheckInReq check) {
        CheckInReqEntity checkInReqEntity = new CheckInReqEntity();

        checkInReqEntity.setUserId(check.getUserId());
        checkInReqEntity.setState(check.getState());
        return checkInReqEntity;
    }

}
