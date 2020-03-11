package example.com.data.repository;

import example.com.data.entity.mapper.OverrideEntityMapper;
import example.com.data.net.Service;
import example.com.domain.model.BaseResponse;
import example.com.domain.model.OverrideHistoryRespList;
import example.com.domain.model.OverrideReq;
import example.com.domain.model.OverrideResp;
import example.com.domain.model.OverrideRespList;
import example.com.domain.repository.OverrideRepository;
import io.reactivex.Scheduler;
import io.reactivex.Single;

public class OverrideRepositoryImpl implements OverrideRepository {

    private final OverrideEntityMapper overrideMapper;
    private final Scheduler scheduler;
    private final Service service;

    public OverrideRepositoryImpl(OverrideEntityMapper overrideMapper, Scheduler scheduler, Service service) {
        this.overrideMapper = overrideMapper;
        this.scheduler = scheduler;
        this.service = service;
    }

    @Override
    public Single<OverrideRespList> doGetOverrideList() {
        return Single.defer(service::getOverrideList)
                .map(overrideMapper::getOverrideListToDomain)
                .subscribeOn(scheduler);
    }

    @Override
    public Single<BaseResponse> doOverrideUser(OverrideReq overrideReq) {
        return Single.defer(()->service.overrideUser(overrideMapper.overrideReqToData(overrideReq)))
                .map(overrideMapper::baseResponseToDomain)
                .subscribeOn(scheduler);
    }

    @Override
    public Single<BaseResponse> doAcceptOverride(OverrideResp overrideResp) {
        return Single.defer(()->service.acceptOverride(overrideMapper.acceptOverrideToData(overrideResp)))
                .map(overrideMapper::baseResponseToDomain)
                .subscribeOn(scheduler);
    }

    @Override
    public Single<BaseResponse> doRejectOverride(OverrideResp overrideResp) {
        return Single.defer(()->service.rejectOverride(overrideResp.getId()))
                .map(overrideMapper::baseResponseToDomain)
                .subscribeOn(scheduler);
    }

    @Override
    public Single<OverrideHistoryRespList> doGetOverrideHistoryList(String id) {
        return Single.defer(()->service.historyOverride(id))
                .map(overrideMapper::overrideHistory)
                .subscribeOn(scheduler);
    }

    @Override
    public Single<BaseResponse> doDeletePendingOverride(String overrideId) {
        return Single.defer(()->service.deletePendingOverride(overrideId))
                .map(overrideMapper::baseResponseToDomain)
                .subscribeOn(scheduler);
    }
}
