package example.com.absensiapp.viewmodel;

import android.util.Log;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import example.com.absensiapp.di.DaggerUserComponent;
import example.com.absensiapp.model.BaseResponseModel;
import example.com.absensiapp.model.CheckInReqModel;
import example.com.absensiapp.model.UserModel;
import example.com.absensiapp.model.UserRespModel;
import example.com.absensiapp.model.mapper.BaseResponseMapper;
import example.com.absensiapp.model.mapper.UserMapper;
import example.com.domain.usecase.user.AddUserUseCase;
import example.com.domain.usecase.user.CheckInUseCase;
import example.com.domain.usecase.user.DeleteUserUseCase;
import example.com.domain.usecase.user.GetUserListUseCase;
import example.com.domain.usecase.user.GetUserUseCase;
import example.com.domain.usecase.user.LoginUseCase;
import example.com.domain.usecase.user.UpdateUserUseCase;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;

public class UserViewModel extends ViewModel {

    private MutableLiveData<UserRespModel> userResp;
    private MutableLiveData<BaseResponseModel> baseResp;
    private MutableLiveData<UserModel> user;

    public UserViewModel() {
        DaggerUserComponent.create().inject(this);
    }

    @Inject
    public Scheduler scheduler;
    @Inject
    public UserMapper userMapper;
    @Inject
    public BaseResponseMapper baseResponseMapper;
    //Inject UseCase
    @Inject
    public LoginUseCase loginUseCase;
    @Inject
    public GetUserListUseCase getUserListUseCase;
    @Inject
    public DeleteUserUseCase deleteUserUseCase;
    @Inject
    public AddUserUseCase addUserUseCase;
    @Inject
    public UpdateUserUseCase updateUserUseCase;
    @Inject
    public GetUserUseCase getUserUseCase;
    @Inject
    public CheckInUseCase checkInUseCase;

    public LiveData<UserRespModel> getRespUser() {
        if(userResp == null) {
            userResp = new MutableLiveData<>();
            loadUserResp();
        }
        return userResp;
    }

    public LiveData<UserModel> getuser() {
        if(user == null) {
            user = new MutableLiveData<>();
        }
        return user;
    }

    public LiveData<BaseResponseModel> getBaseResp() {
        if(baseResp == null) {
            baseResp = new MutableLiveData<>();
        }
        return baseResp;
    }

    private void loadUserResp() {
        getUserListUseCase.execute()
                .map(userMapper::userRespToView)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserRespModel>() {
                    @Override
                    public void onSuccess(UserRespModel userRespModel) {
                        userResp.setValue(userRespModel);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void deleteUser(String userId) {
        deleteUserUseCase.execute(userId)
                .map(baseResponseMapper::baseResponseToView)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponseModel>() {
                    @Override
                    public void onSuccess(BaseResponseModel baseResponseModel) {
                        baseResp.setValue(baseResponseModel);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void addUser(UserModel userModel) {
        addUserUseCase.execute(userMapper.userToDomain(userModel))
                .map(baseResponseMapper::baseResponseToView)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponseModel>() {
                    @Override
                    public void onSuccess(BaseResponseModel baseResponseModel) {
                        baseResp.setValue(baseResponseModel);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void updateUser(UserModel userModel) {
        updateUserUseCase.execute(userMapper.userToDomain(userModel))
                .map(baseResponseMapper::baseResponseToView)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponseModel>() {
                    @Override
                    public void onSuccess(BaseResponseModel baseResponseModel) {
                        baseResp.setValue(baseResponseModel);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void login(UserModel userModel) {
        loginUseCase.execute(userMapper.userToDomain(userModel))
                .map(userMapper::userToView)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserModel>() {
                    @Override
                    public void onSuccess(UserModel userModel) {
                        user.setValue(userModel);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void checkInUser(CheckInReqModel check) {
        checkInUseCase.execute(userMapper.checkToDomain(check))
                .map(baseResponseMapper::baseResponseToView)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponseModel>() {
                    @Override
                    public void onSuccess(BaseResponseModel baseResponseModel) {
                        baseResp.setValue(baseResponseModel);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

}
