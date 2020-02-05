package example.com.domain.repository;

import example.com.domain.model.BaseResponse;
import example.com.domain.model.User;
import io.reactivex.Single;

public interface BaseResponseRepository {

    Single<BaseResponse> doAddUser(User user);
    Single<BaseResponse> doDeleteUser(String userId);
    Single<BaseResponse> doUpdateUser(String userId, User user);
}
