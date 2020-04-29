package example.com.absensiapp.model.mapper;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import example.com.absensiapp.model.CheckInReqModel;
import example.com.absensiapp.model.ResetPasswordReqModel;
import example.com.absensiapp.model.ResetPasswordRespListModel;
import example.com.absensiapp.model.ResetPasswordRespModel;
//import example.com.absensiapp.model.UploadImageReqModel;
import example.com.absensiapp.model.UserModel;
import example.com.absensiapp.model.UserListModel;
import example.com.domain.model.CheckInReq;
import example.com.domain.model.ResetPasswordReq;
import example.com.domain.model.ResetPasswordResp;
import example.com.domain.model.ResetPasswordRespList;
//import example.com.domain.model.UploadImageReq;
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

        userModel.setErrorCode(userFromDomain.getErrorCode());
        userModel.setErrorMessage(userFromDomain.getErrorMessage());
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

    public ResetPasswordReq resetPasswordToDomain(ResetPasswordReqModel reqModel) {
        ResetPasswordReq resetPasswordReq = new ResetPasswordReq();

        resetPasswordReq.setUserId(reqModel.getUserId());
        resetPasswordReq.setPassword(reqModel.getPassword());

        return  resetPasswordReq;
    }

    public ResetPasswordRespListModel resetListRespToView(ResetPasswordRespList resetListFromDomain) {
        ResetPasswordRespListModel resetList = new ResetPasswordRespListModel();
        List<ResetPasswordRespModel> resetModelList = new ArrayList<>();
        Log.d("NILAI", resetListFromDomain.getResetPassList().toString());
        for(int i=0;i<resetListFromDomain.getResetPassList().size();i++)
        {
            ResetPasswordResp resetDataFromDomain = resetListFromDomain.getResetPassList().get(i);
            ResetPasswordRespModel resetModel = new ResetPasswordRespModel();

            resetModel.setName(resetDataFromDomain.getName());
            resetModel.setUserId(resetDataFromDomain.getUserId());


            resetModelList.add(resetModel);
        }
        resetList.setResetPassList(resetModelList);
        Log.d("NILAI", resetList.toString());
        return resetList;
    }

//    public UploadImageReq imageToDomain(UploadImageReqModel uploadImageReqModel) {
//        UploadImageReq uploadImageReq = new UploadImageReq();
//
//        uploadImageReq.setMultipart(uploadImageReqModel.getMultipart());
//        uploadImageReq.setUserid(uploadImageReqModel.getUserid());
//
//        return uploadImageReq;
//    }
}
