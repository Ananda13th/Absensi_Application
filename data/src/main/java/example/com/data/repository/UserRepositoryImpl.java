package example.com.data.repository;

import example.com.data.entity.mapper.UserEntityMapper;
import example.com.data.net.Service;
import example.com.data.net.ServiceGenerator;
import example.com.domain.model.User;
import example.com.domain.model.UserList;
import example.com.domain.repository.UserRepository;
import io.reactivex.Scheduler;
import io.reactivex.Single;

public class UserRepositoryImpl implements UserRepository {

    private final UserEntityMapper userMapper;
    private final Scheduler scheduler;
    private final Service service;

    public UserRepositoryImpl(UserEntityMapper userMapper, Scheduler scheduler, Service service) {
        this.userMapper = userMapper;
        this.scheduler = scheduler;
        this.service = service;
    }

    @Override
    public Single<UserList> doGetListUser() {
        final Service service = ServiceGenerator.getService();
        return Single.defer(()->service.getUserList())
                .map(userMapper::userRespToDomain)
                .subscribeOn(scheduler);
    }

    @Override
    public Single<User> doGetUser(String userId) {
        final Service service = ServiceGenerator.getService();
        return Single.defer(()->service.getUser(userId))
                .map(userMapper::userToDomain)
                .subscribeOn(scheduler);
    }

    @Override
    public Single<User> doLogin(User user) {
        final Service service = ServiceGenerator.getService();
        return Single.defer(()->service.loginUser(userMapper.userToData(user)))
                .map(userMapper::userToDomain)
                .subscribeOn(scheduler);
    }
}
