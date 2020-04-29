package example.com.domain.repository;

import example.com.domain.model.BaseResponse;
import example.com.domain.model.CheckInReq;
import example.com.domain.model.ResetPasswordReq;
import example.com.domain.model.ResetPasswordRespList;
import example.com.domain.model.User;
import example.com.domain.model.UserList;
import io.reactivex.Single;

public interface UserRepository {

    Single<UserList> doGetListUser();
    Single<User> doGetUser(String userId);
    Single<User> doLogin(User user);
    Single<BaseResponse> doAddUser(User user);
    Single<BaseResponse> doDeleteUser(String userId);
    Single<BaseResponse> doUpdateUser(String userId, User user);
    Single<BaseResponse> doCheckUser(CheckInReq check);
    Single<BaseResponse> doRequestResetPassword(ResetPasswordReq resetPasswordReq);
    Single<ResetPasswordRespList> doGetResetPasswordList();
    Single<BaseResponse> doUpdatePassword(ResetPasswordReq resetPasswordReq);
    Single<BaseResponse> doDeletePasswordRequest(String userid);
//    Single<BaseResponse> doUploadImage(RequestBody userId, MultipartBody.Part[] image);
}
