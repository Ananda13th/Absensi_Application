package example.com.data;
import org.junit.Test;
import org.mockito.Mockito;

import example.com.data.entity.BaseResponseEntity;
import example.com.data.entity.OverrideHistoryRespListEntity;
import example.com.data.entity.OverrideReqEntity;
import example.com.data.entity.OverrideRespEntity;
import example.com.data.entity.OverrideRespListEntity;
import example.com.data.entity.mapper.OverrideEntityMapper;
import example.com.data.net.Service;
import example.com.data.repository.OverrideRepositoryImpl;
import example.com.domain.model.OverrideReq;
import example.com.domain.model.OverrideResp;
import example.com.domain.repository.OverrideRepository;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class OverrideUnitTest {
    @Test
    public void t001_doGetOverrideListShouldHitCorrectService() {
        Service service = Mockito.mock(Service.class);

        Mockito.when(service.getOverrideList()).thenReturn(Single.just(new OverrideRespListEntity()));

        OverrideRepository overrideRepository = new OverrideRepositoryImpl(new OverrideEntityMapper(), Schedulers.io(), service);

        overrideRepository.doGetOverrideList();

        Mockito.verify(service, Mockito.times(1)).getOverrideList();

    }

    @Test
    public void t002_doOverrideUserShouldHitCorrectService() {
        Service service = Mockito.mock(Service.class);

        OverrideReqEntity overrideReqEntity = new OverrideReqEntity();
        OverrideReq overrideReq = new OverrideReq();

        Mockito.when(service.requestOverride(overrideReqEntity)).thenReturn(Single.just(new BaseResponseEntity()));

        OverrideRepository overrideRepository = new OverrideRepositoryImpl(new OverrideEntityMapper(), Schedulers.io(), service);

        overrideRepository.doRequestOverride(overrideReq);

        Mockito.verify(service, Mockito.times(1)).requestOverride(overrideReqEntity);

    }

    @Test
    public void t003_doAcceptOverrideShouldHitCorrectService() {
        Service service = Mockito.mock(Service.class);

        OverrideRespEntity overrideRespEntity = new OverrideRespEntity();
        OverrideResp overrideResp = new OverrideResp();

        Mockito.when(service.approveOverride(overrideRespEntity)).thenReturn(Single.just(new BaseResponseEntity()));

        OverrideRepository overrideRepository = new OverrideRepositoryImpl(new OverrideEntityMapper(), Schedulers.io(), service);

        overrideRepository.doApproveOverride(overrideResp);

        Mockito.verify(service, Mockito.times(1)).approveOverride(overrideRespEntity);

    }


    @Test
    public void t004_doRejecttOverrideShouldHitCorrectService() {
        Service service = Mockito.mock(Service.class);

        OverrideResp overrideResp = new OverrideResp();
        overrideResp.setId("512");

        Mockito.when(service.rejectOverride(overrideResp.getId())).thenReturn(Single.just(new BaseResponseEntity()));

        OverrideRepository overrideRepository = new OverrideRepositoryImpl(new OverrideEntityMapper(), Schedulers.io(), service);

        overrideRepository.doRejectOverride(overrideResp);

        Mockito.verify(service, Mockito.times(1)).rejectOverride("512");

    }

    @Test
    public void t005_doGetOverrideHistoryListShouldHitCorrectService() {
        Service service = Mockito.mock(Service.class);

        Mockito.when(service.getOverrideHistoryList("512")).thenReturn(Single.just(new OverrideHistoryRespListEntity()));

        OverrideRepository overrideRepository = new OverrideRepositoryImpl(new OverrideEntityMapper(), Schedulers.io(), service);

        overrideRepository.doGetOverrideHistoryList("512");

        Mockito.verify(service, Mockito.times(1)).getOverrideHistoryList("512");
    }

    @Test
    public void t006_doDeletePendingOverrideShouldHitCorrectService() {
        Service service = Mockito.mock(Service.class);

        Mockito.when(service.deletePendingOverride("1")).thenReturn(Single.just(new BaseResponseEntity()));

        OverrideRepository overrideRepository = new OverrideRepositoryImpl(new OverrideEntityMapper(), Schedulers.io(), service);

        overrideRepository.doDeletePendingOverride("1");

        Mockito.verify(service, Mockito.times(1)).deletePendingOverride("1");

    }
}
