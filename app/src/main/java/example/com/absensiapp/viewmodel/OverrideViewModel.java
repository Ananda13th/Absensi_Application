package example.com.absensiapp.viewmodel;

import android.annotation.SuppressLint;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import example.com.absensiapp.di.DaggerUserComponent;
import example.com.absensiapp.model.BaseResponseModel;
import example.com.absensiapp.model.OverrideHistoryRespListModel;
import example.com.absensiapp.model.OverrideRespListModel;
import example.com.absensiapp.model.OverrideReqModel;
import example.com.absensiapp.model.OverrideRespModel;
import example.com.absensiapp.model.mapper.OverrideMapper;
import example.com.domain.usecase.override.AcceptOverrideUseCase;
import example.com.domain.usecase.override.DeletePendingOverrideUseCase;
import example.com.domain.usecase.override.GetOverrideHistoryUseCase;
import example.com.domain.usecase.override.GetOverrideListUseCase;
import example.com.domain.usecase.override.OverrideUseCase;
import example.com.domain.usecase.override.RejectOverrideUseCase;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;

public class OverrideViewModel extends ViewModel {

    private MutableLiveData<OverrideReqModel> overrideResp;
    private MutableLiveData<BaseResponseModel> baseResp;
    private MutableLiveData<OverrideRespListModel> overrideList;
    private MutableLiveData<OverrideHistoryRespListModel> overrideHistoryList;

    public OverrideViewModel() {
        DaggerUserComponent.create().inject(this);
    }

    @Inject
    Scheduler scheduler;
    @Inject
    OverrideMapper overrideMapper;
    @Inject
    OverrideUseCase overrideUseCase;
    @Inject
    GetOverrideListUseCase getOverrideListUseCase;
    @Inject
    AcceptOverrideUseCase acceptOverrideUseCase;
    @Inject
    RejectOverrideUseCase rejectOverrideUseCase;
    @Inject
    GetOverrideHistoryUseCase getOverrideHistoryUseCase;
    @Inject
    DeletePendingOverrideUseCase deletePendingOverrideUseCase;

    public LiveData<OverrideReqModel> getOverride() {
        if(overrideResp == null) {
            overrideResp = new MutableLiveData<>();
        }
        return overrideResp;
    }

    public LiveData<OverrideRespListModel> getOverrideList() {
        if(overrideList == null) {
            overrideList = new MutableLiveData<>();
        }
        loadOverrideList();
        return overrideList;
    }

    public LiveData<OverrideHistoryRespListModel> getOverrideHistory(String id) {
        if(overrideHistoryList == null) {
            overrideHistoryList = new MutableLiveData<>();
        }
        getOverrideHistoryList(id);
        return overrideHistoryList;
    }

    public LiveData<BaseResponseModel> getBaseResp() {
        if(baseResp == null) {
            baseResp = new MutableLiveData<>();
        }
        return baseResp;
    }

    @SuppressLint("CheckResult")
    public void sendOverrideReq(OverrideReqModel overrideReqModel) {
        overrideUseCase.execute(overrideMapper.requestOverrideToDomain(overrideReqModel))
                .map(overrideMapper::baseResponseToView)
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
    private void loadOverrideList() {
        getOverrideListUseCase.execute()
                .map(overrideMapper::getOverrideListToView)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<OverrideRespListModel>() {
                    @Override
                    public void onSuccess(OverrideRespListModel overrideRespListModel) {
                        overrideList.setValue(overrideRespListModel);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void acceptOverride(OverrideRespModel overrideRespModel) {
        acceptOverrideUseCase.execute(overrideMapper.acceptOverrideToDomain(overrideRespModel))
                .map(overrideMapper::baseResponseToView)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponseModel>() {
                    @Override
                    public void onSuccess(BaseResponseModel baseResponseModel) {
                        baseResp.setValue(baseResponseModel);
                        getOverrideList();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    @SuppressLint("CheckResult")
    public void rejectOverride(OverrideRespModel overrideRespModel) {
        rejectOverrideUseCase.execute(overrideMapper.rejectOverrideToDomain(overrideRespModel))
                .map(overrideMapper::baseResponseToView)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponseModel>() {
                    @Override
                    public void onSuccess(BaseResponseModel baseResponseModel) {
                        baseResp.setValue(baseResponseModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.getMessage();
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void getOverrideHistoryList(String userid) {
        getOverrideHistoryUseCase.execute(userid)
                .map(overrideMapper::overrideHistoryToView)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<OverrideHistoryRespListModel>() {
                    @Override
                    public void onSuccess(OverrideHistoryRespListModel overrideHistoryRespListModel) {
                        overrideHistoryList.setValue(overrideHistoryRespListModel);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void deletePendingOverride(String overrideId) {
        deletePendingOverrideUseCase.execute(overrideId)
                .map(overrideMapper::baseResponseToView)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponseModel>() {
                    @Override
                    public void onSuccess(BaseResponseModel baseResponseModel) {
                        baseResp.setValue(baseResponseModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.getMessage();
                    }
                });
    }
}
