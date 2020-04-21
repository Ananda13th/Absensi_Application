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
        return service.getOverrideList()
                .map(overrideMapper::getOverrideListToDomain)
                .subscribeOn(scheduler);
    }

    @Override
    public Single<BaseResponse> doRequestOverride(OverrideReq overrideReq) {
        return service.requestOverride(overrideMapper.overrideReqToData(overrideReq))
                .map(overrideMapper::baseResponseToDomain)
                .subscribeOn(scheduler);
    }

    @Override
    public Single<BaseResponse> doApproveOverride(OverrideResp overrideResp) {
        return service.approveOverride(overrideMapper.acceptOverrideToData(overrideResp))
                .map(overrideMapper::baseResponseToDomain)
                .subscribeOn(scheduler);
    }

    @Override
    public Single<BaseResponse> doRejectOverride(OverrideResp overrideResp) {
        return service.rejectOverride(overrideResp.getId())
                .map(overrideMapper::baseResponseToDomain)
                .subscribeOn(scheduler);
    }

    @Override
    public Single<OverrideHistoryRespList> doGetOverrideHistoryList(String id) {
        return service.getOverrideHistoryList(id)
                .map(overrideMapper::overrideHistory)
                .subscribeOn(scheduler);
    }

    @Override
    public Single<BaseResponse> doDeletePendingOverride(String overrideId) {
        return service.deletePendingOverride(overrideId)
                .map(overrideMapper::baseResponseToDomain)
                .subscribeOn(scheduler);
    }
}
