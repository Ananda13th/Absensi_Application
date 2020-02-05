package example.com.data.repository;

import example.com.data.entity.mapper.UserEntityMapper;
import example.com.data.net.Service;
import example.com.domain.model.BaseResponse;
import example.com.domain.model.User;
import example.com.domain.repository.BaseResponseRepository;
import io.reactivex.Scheduler;
import io.reactivex.Single;

public class BaseResponseRepositoryImpl implements BaseResponseRepository {
    private final UserEntityMapper userMapper;
    private final Scheduler scheduler;
    private final Service service;

    public BaseResponseRepositoryImpl(UserEntityMapper userMapper, Scheduler scheduler, Service service) {
        this.userMapper = userMapper;
        this.scheduler = scheduler;
        this.service = service;
    }

    @Override
    public Single<BaseResponse> doAddUser(User user) {
        return Single.defer(()->service.addUser(userMapper.userToData(user)))
                .map(userMapper::baseResponseToDomain)
                .subscribeOn(scheduler);
    }

    @Override
    public Single<BaseResponse> doDeleteUser(String userId) {
        return Single.defer(()->service.deleteUser(userId))
                .map(userMapper::baseResponseToDomain)
                .subscribeOn(scheduler);
    }

    @Override
    public Single<BaseResponse> doUpdateUser(String userId, User user) {
        return Single.defer(()->service.updateUser(userId,userMapper.userToData(user)))
                .map(userMapper::baseResponseToDomain)
                .subscribeOn(scheduler);
    }
}
