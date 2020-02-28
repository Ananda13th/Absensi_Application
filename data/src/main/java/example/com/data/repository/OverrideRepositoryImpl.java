package example.com.data.repository;

import example.com.data.entity.mapper.OverrideReqEntityMapper;
import example.com.data.net.Service;
import example.com.domain.model.BaseResponse;
import example.com.domain.model.OverrideReq;
import example.com.domain.model.OverrideResp;
import example.com.domain.model.OverrideRespList;
import example.com.domain.repository.OverrideRepository;
import io.reactivex.Scheduler;
import io.reactivex.Single;

public class OverrideRepositoryImpl implements OverrideRepository {

    private final OverrideReqEntityMapper overrideMapper;
    private final Scheduler scheduler;
    private final Service service;

    public OverrideRepositoryImpl(OverrideReqEntityMapper overrideMapper, Scheduler scheduler, Service service) {
        this.overrideMapper = overrideMapper;
        this.scheduler = scheduler;
        this.service = service;
    }

    @Override
    public Single<OverrideRespList> doGetOverrideList() {
        return Single.defer(()->service.getOverrideList())
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
    public Single<BaseResponse> doRejectOverride(String id) {
        return Single.defer(()->service.rejectOverride(id))
                .map(overrideMapper::baseResponseToDomain)
                .subscribeOn(scheduler);
    }
}
