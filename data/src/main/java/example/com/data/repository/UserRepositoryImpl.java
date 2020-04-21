package example.com.data.repository;

import example.com.data.entity.mapper.UserEntityMapper;
import example.com.data.net.Service;
import example.com.data.net.ServiceGenerator;
import example.com.domain.model.BaseResponse;
import example.com.domain.model.CheckInReq;
import example.com.domain.model.User;
import example.com.domain.model.UserList;
import example.com.domain.repository.UserRepository;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
        return service.getUserList()
                .map(userMapper::userRespToDomain)
                .subscribeOn(scheduler);
    }

    @Override
    public Single<User> doGetUser(String userId) {
        return service.getUser(userId)
                .map(userMapper::userToDomain)
                .subscribeOn(scheduler);
    }

    @Override
    public Single<User> doLogin(User user) {
        return service.loginUser(userMapper.userToData(user))
                .map(userMapper::userToDomain)
                .subscribeOn(scheduler);
    }

    @Override
    public Single<BaseResponse> doAddUser(User user) {
        return service.addUser(userMapper.userToData(user))
                .map(userMapper::baseResponseToDomain)
                .subscribeOn(scheduler);
    }

    @Override
    public Single<BaseResponse> doDeleteUser(String userId) {
        return service.deleteUser(userId)
                .map(userMapper::baseResponseToDomain)
                .subscribeOn(scheduler);
    }

    @Override
    public Single<BaseResponse> doUpdateUser(String userId, User user) {
        return service.updateUser(userId,userMapper.userToData(user))
                .map(userMapper::baseResponseToDomain)
                .subscribeOn(scheduler);
    }

    @Override
    public Single<BaseResponse> doCheckUser(CheckInReq check) {
        return service.checkUser(userMapper.userToData(check))
                .map(userMapper::baseResponseToDomain)
                .subscribeOn(scheduler);
    }

//    @Override
//    public Single<BaseResponse> doUploadImage(RequestBody userId, MultipartBody.Part[] image) {
//        return service.uploadImage(userId, image)
//                .map(userMapper::baseResponseToDomain)
//                .subscribeOn(scheduler);
//    }

}
