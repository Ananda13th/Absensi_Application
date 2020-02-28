package example.com.domain.repository;

import example.com.domain.model.BaseResponse;
import example.com.domain.model.CheckInReq;
import example.com.domain.model.OverrideReq;
import example.com.domain.model.User;
import io.reactivex.Single;
import okhttp3.MultipartBody;

public interface BaseResponseRepository {

    Single<BaseResponse> doAddUser(User user);
    Single<BaseResponse> doDeleteUser(String userId);
    Single<BaseResponse> doUpdateUser(String userId, User user);
    Single<BaseResponse> doCheckUser(CheckInReq check);
    //Single<BaseResponse> doUploadImage(String userId, MultipartBody.Part image);
}
