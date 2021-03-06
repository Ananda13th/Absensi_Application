package example.com.data;

import org.junit.Assert;
import org.junit.Test;

import example.com.data.entity.mapper.OverrideEntityMapper;
import example.com.data.net.Service;
import example.com.data.net.ServiceGenerator;
import example.com.data.repository.OverrideRepositoryImpl;
import example.com.domain.model.BaseResponse;
import example.com.domain.model.OverrideHistoryRespList;
import example.com.domain.model.OverrideReq;
import example.com.domain.model.OverrideResp;
import example.com.domain.model.OverrideRespList;
import example.com.domain.usecase.override.ApproveOverrideUseCase;
import example.com.domain.usecase.override.GetOverrideHistoryUseCase;
import example.com.domain.usecase.override.GetOverrideListUseCase;
import example.com.domain.usecase.override.RequestOverrideUseCase;
import example.com.domain.usecase.override.RejectOverrideUseCase;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

public class OverrideIntergrationTest {

    @Test
    public void T001_OverrideUseCaseTest() {
        OverrideEntityMapper mapper = new OverrideEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        OverrideRepositoryImpl overrideRepository = new OverrideRepositoryImpl(mapper, scheduler, service);
        RequestOverrideUseCase requestOverrideUseCase = new RequestOverrideUseCase(overrideRepository);
        OverrideReq overrideReq = new OverrideReq();
        Single<BaseResponse> resp = requestOverrideUseCase.execute(overrideReq);
        TestObserver<BaseResponse> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        Assert.assertEquals("00", testObserver.values().get(0).getErrorCode());

    }

    @Test
    public void T002_GetOverrideListUseCaseTest() {
        OverrideEntityMapper mapper = new OverrideEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        OverrideRepositoryImpl overrideRepository = new OverrideRepositoryImpl(mapper, scheduler, service);
        GetOverrideListUseCase getOverrideListUseCase = new GetOverrideListUseCase(overrideRepository);
        Single<OverrideRespList> resp = getOverrideListUseCase.execute();
        TestObserver<OverrideRespList> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        Assert.assertEquals("00", testObserver.values().get(0).getErrorCode());

    }

    @Test
    public void T003_AcceptOverrideUseCaseTest() {
        OverrideEntityMapper mapper = new OverrideEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        OverrideRepositoryImpl overrideRepository = new OverrideRepositoryImpl(mapper, scheduler, service);
        ApproveOverrideUseCase approveOverrideUseCasetOverrideListUseCase = new ApproveOverrideUseCase(overrideRepository);
        OverrideResp overrideResp = new OverrideResp();
        Single<BaseResponse> resp = approveOverrideUseCasetOverrideListUseCase.execute(overrideResp);
        TestObserver<BaseResponse> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        Assert.assertEquals("00", testObserver.values().get(0).getErrorCode());
    }

    @Test
    public void T004_RejectOverrideUseCaseTest() {
        OverrideEntityMapper mapper = new OverrideEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        OverrideRepositoryImpl overrideRepository = new OverrideRepositoryImpl(mapper, scheduler, service);
        RejectOverrideUseCase rejectOverrideUseCase = new  RejectOverrideUseCase(overrideRepository);
        OverrideResp overrideResp = new OverrideResp();
        overrideResp.setId("1");
        Single<BaseResponse> resp = rejectOverrideUseCase.execute(overrideResp);
        TestObserver<BaseResponse> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        Assert.assertEquals("00", testObserver.values().get(0).getErrorCode());

    }

    @Test
    public void T005_GetOverrideHistoryUseCaseTest() {
        OverrideEntityMapper mapper = new OverrideEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        OverrideRepositoryImpl overrideRepository = new OverrideRepositoryImpl(mapper, scheduler, service);
        GetOverrideHistoryUseCase getOverrideHistoryUseCase = new   GetOverrideHistoryUseCase(overrideRepository);
        OverrideResp overrideResp = new OverrideResp();
        overrideResp.setId("U536735");
        Single<OverrideHistoryRespList> resp = getOverrideHistoryUseCase.execute(overrideResp.getId());
        TestObserver<OverrideHistoryRespList> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        Assert.assertNotNull(resp);
        testObserver.assertNoErrors();
        Assert.assertEquals("00", testObserver.values().get(0).getErrorCode());
    }
}
