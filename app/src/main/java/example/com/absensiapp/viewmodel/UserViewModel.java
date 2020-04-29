package example.com.absensiapp.viewmodel;

import android.annotation.SuppressLint;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import example.com.absensiapp.di.DaggerUserComponent;
import example.com.absensiapp.model.BaseResponseModel;
import example.com.absensiapp.model.CheckInReqModel;
import example.com.absensiapp.model.ResetPasswordReqModel;
import example.com.absensiapp.model.ResetPasswordRespListModel;
import example.com.absensiapp.model.UserModel;
import example.com.absensiapp.model.UserListModel;
import example.com.absensiapp.model.mapper.BaseResponseMapper;
import example.com.absensiapp.model.mapper.UserMapper;
import example.com.domain.usecase.user.AddUserUseCase;
import example.com.domain.usecase.user.CheckInUseCase;
import example.com.domain.usecase.user.DeletePasswordRequestUseCase;
import example.com.domain.usecase.user.DeleteUserUseCase;
import example.com.domain.usecase.user.GetResetPasswordListUseCase;
import example.com.domain.usecase.user.GetUserListUseCase;
import example.com.domain.usecase.user.GetUserUseCase;
import example.com.domain.usecase.user.LoginUseCase;
import example.com.domain.usecase.user.RequestResetPasswordUseCase;
import example.com.domain.usecase.user.UpdatePasswordUseCase;
import example.com.domain.usecase.user.UpdateUserUseCase;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;

public class UserViewModel extends ViewModel {

    private MutableLiveData<UserListModel> userResp;
    private MutableLiveData<BaseResponseModel> baseResp;
    private MutableLiveData<UserModel> user;
    private MutableLiveData<ResetPasswordRespListModel> resetResp;

    public UserViewModel() {
        DaggerUserComponent.create().inject(this);
    }

    @Inject
    Scheduler scheduler;
    @Inject
    UserMapper userMapper;
    @Inject
    BaseResponseMapper baseResponseMapper;

    //Inject UseCase
    @Inject
    LoginUseCase loginUseCase;
    @Inject
    GetUserListUseCase getUserListUseCase;
    @Inject
    DeleteUserUseCase deleteUserUseCase;
    @Inject
    AddUserUseCase addUserUseCase;
    @Inject
    UpdateUserUseCase updateUserUseCase;
    @Inject
    GetUserUseCase getUserUseCase;
    @Inject
    CheckInUseCase checkInUseCase;
    @Inject
    RequestResetPasswordUseCase requestResetPasswordUseCase;
    @Inject
    GetResetPasswordListUseCase getResetPasswordListUseCase;
    @Inject
    DeletePasswordRequestUseCase deletePasswordRequestUseCase;
    @Inject
    UpdatePasswordUseCase updatePasswordUseCase;
//    @Inject
//    UploadImageUseCase uploadImageUseCase;

    public LiveData<UserListModel> getRespUser() {
        if(userResp == null) {
            userResp = new MutableLiveData<>();
            loadUserResp();
        }
        else
            loadUserResp();
        return userResp;
    }

    public LiveData<UserModel> getUser() {
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

    public LiveData<ResetPasswordRespListModel> getResetResp() {
        if(resetResp == null) {
            resetResp = new MutableLiveData<>();
            getResetPasswordList();
        }
        else
            getResetPasswordList();

        return resetResp;
    }

    @SuppressLint("CheckResult")
    public void loadUserResp() {
        getUserListUseCase.execute()
                .map(userMapper::userListRespToView)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserListModel>() {
                    @Override
                    public void onSuccess(UserListModel userListModel) {
                        userResp.setValue(userListModel);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void deleteUser(String userId) {
        deleteUserUseCase.execute(userId)
                .map(baseResponseMapper::baseResponseToView)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponseModel>() {
                    @Override
                    public void onSuccess(BaseResponseModel baseResponseModel) {
                        baseResp.setValue(baseResponseModel);
                        getRespUser();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void addUser(UserModel userModel) {
        addUserUseCase.execute(userMapper.userToDomain(userModel))
                .map(baseResponseMapper::baseResponseToView)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponseModel>() {
                    @Override
                    public void onSuccess(BaseResponseModel baseResponseModel) {
                        baseResp.setValue(baseResponseModel);
                        getRespUser();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @SuppressLint("CheckResult")
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

    @SuppressLint("CheckResult")
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
                        e.getMessage();
                    }
                });
    }

    @SuppressLint("CheckResult")
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

    @SuppressLint("CheckResult")
    public void requestResetPassword(ResetPasswordReqModel reqModel) {
        requestResetPasswordUseCase.execute(userMapper.resetPasswordToDomain(reqModel))
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

    @SuppressLint("CheckResult")
    public void getResetPasswordList() {
        getResetPasswordListUseCase.execute()
                .map(userMapper::resetListRespToView)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ResetPasswordRespListModel>() {
                    @Override
                    public void onSuccess(ResetPasswordRespListModel resetPasswordRespListModel) {
                        resetResp.setValue(resetPasswordRespListModel);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void deleteRequestResetPassword(String userid) {
        deletePasswordRequestUseCase.execute(userid)
                .map(baseResponseMapper::baseResponseToView)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponseModel>() {
                    @Override
                    public void onSuccess(BaseResponseModel baseResponseModel) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

//    @SuppressLint("CheckResult")
//    public void uploadImage(UploadImageReqModel uploadImageReqModel) {
//        uploadImageUseCase.execute(userMapper.imageToDomain(uploadImageReqModel))
//                .map(baseResponseMapper::baseResponseToView)
//                .subscribeOn(scheduler)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<BaseResponseModel>() {
//                    @Override
//                    public void onSuccess(BaseResponseModel baseResponseModel) {
//                        baseResp.setValue(baseResponseModel);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                });
//    }

    public void clearViewModelValue() {
        baseResp.setValue(new BaseResponseModel());
    }

}
