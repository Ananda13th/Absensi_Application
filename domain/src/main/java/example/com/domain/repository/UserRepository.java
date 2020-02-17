package example.com.domain.repository;

import example.com.domain.model.User;
import example.com.domain.model.UserList;
import io.reactivex.Single;

public interface UserRepository {

    Single<UserList> doGetListUser();
    Single<User> doGetUser(String userId);
    Single<User> doLogin(User user);
}
