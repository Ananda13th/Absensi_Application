package example.com.domain.repository;

import example.com.domain.model.User;
import example.com.domain.model.UserResp;
import io.reactivex.Single;

public interface UserRepository {

    Single<UserResp> doGetListUser();
    Single<User> doGetUser(String userId);
    Single<User> doLogin(User user);
}
