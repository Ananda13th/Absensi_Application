package example.com.domain.repository;

import example.com.domain.model.BaseResponse;
import example.com.domain.model.CheckInReq;
import example.com.domain.model.User;
import example.com.domain.model.UserList;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface UserRepository {

    Single<UserList> doGetListUser();
    Single<User> doGetUser(String userId);
    Single<User> doLogin(User user);
    Single<BaseResponse> doAddUser(User user);
    Single<BaseResponse> doDeleteUser(String userId);
    Single<BaseResponse> doUpdateUser(String userId, User user);
    Single<BaseResponse> doCheckUser(CheckInReq check);
//    Single<BaseResponse> doUploadImage(RequestBody userId, MultipartBody.Part[] image);
}
