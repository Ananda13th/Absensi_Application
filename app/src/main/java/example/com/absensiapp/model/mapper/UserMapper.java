package example.com.absensiapp.model.mapper;

import java.util.ArrayList;
import java.util.List;

import example.com.absensiapp.model.CheckInReqModel;
import example.com.absensiapp.model.UserModel;
import example.com.absensiapp.model.UserListModel;
import example.com.domain.model.CheckInReq;
import example.com.domain.model.User;
import example.com.domain.model.UserList;

public class UserMapper extends BaseResponseMapper {

    public UserListModel userListRespToView(UserList userRespFromDomain) {
        UserListModel userListModel = new UserListModel();
        List<UserModel> userList = new ArrayList<>();

        for(int i=0;i<userRespFromDomain.getUserList().size();i++)
        {
            User user = userRespFromDomain.getUserList().get(i);
            UserModel userModel = new UserModel();

            userModel.setName(user.getName());
            userModel.setPassword(user.getPassword());
            userModel.setUserId(user.getUserId());
            userModel.setRole(user.getRole());
            userList.add(userModel);
        }
        userListModel.setUserList(userList);
        return userListModel;
    }

    public UserModel userToView(User userFromDomain) {
        UserModel userModel = new UserModel();

        userModel.setName(userFromDomain.getName());
        userModel.setPassword(userFromDomain.getPassword());
        userModel.setUserId(userFromDomain.getUserId());
        userModel.setRole(userFromDomain.getRole());
        return userModel;
    }

    public User userToDomain(UserModel userFromView) {
        User user = new User();

        user.setName(userFromView.getName());
        user.setPassword(userFromView.getPassword());
        user.setUserId(userFromView.getUserId());
        return user;
    }

    public CheckInReq checkToDomain(CheckInReqModel check) {
        CheckInReq checkInReq = new CheckInReq();

        checkInReq.setUserId(check.getUserId());
        checkInReq.setState(check.getState());

        return checkInReq;
    }
}
