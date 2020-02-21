package example.com.absensiapp.viewmodel;

import android.annotation.SuppressLint;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import example.com.absensiapp.di.DaggerUserComponent;
import example.com.absensiapp.model.InputHistoryModel;
import example.com.absensiapp.model.OutputHistoryModel;
import example.com.absensiapp.model.mapper.HistoryMapper;
import example.com.data.entity.mapper.HistoryEntityMapper;
import example.com.domain.usecase.history.SearchHistoryUseCase;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;

public class HistoryViewModel extends ViewModel {

    private MutableLiveData<OutputHistoryModel> historyResp;

    public HistoryViewModel() {
        DaggerUserComponent.create().inject(this);
    }

    @Inject
    Scheduler scheduler;

    @Inject
    HistoryMapper historyMapper;

    @Inject
    HistoryEntityMapper historyEntityMapper;

    @Inject
    SearchHistoryUseCase searchHistoryUseCase;

    public LiveData<OutputHistoryModel> getHistory() {
        if(historyResp == null) {
            historyResp = new MutableLiveData<>();
        }
        return historyResp;
    }

    @SuppressLint("CheckResult")
    public void loadHistory(InputHistoryModel inputHistoryModel) {
        searchHistoryUseCase.execute(historyMapper.inputHistoryToDomain(inputHistoryModel))
                .map(historyMapper::outputHistoryRespToView)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<OutputHistoryModel>() {
                    @Override
                    public void onSuccess(OutputHistoryModel outputHistoryModel) {
                        historyResp.setValue(outputHistoryModel);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void resetHistory() {
        historyResp.setValue(null);
    }



}
