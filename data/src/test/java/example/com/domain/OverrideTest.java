package example.com.domain;

import org.junit.Assert;
import org.junit.Test;

import example.com.data.entity.mapper.OverrideReqEntityMapper;
import example.com.data.net.Service;
import example.com.data.net.ServiceGenerator;
import example.com.data.repository.OverrideRepositoryImpl;
import example.com.domain.model.BaseResponse;
import example.com.domain.model.OverrideReq;
import example.com.domain.model.OverrideResp;
import example.com.domain.model.OverrideRespList;
import example.com.domain.usecase.override.AcceptOverrideUseCase;
import example.com.domain.usecase.override.GetOverrideListUseCase;
import example.com.domain.usecase.override.OverrideUseCase;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

public class OverrideTest {

    @Test
    public void T001_OverrideUseCaseTest() {
        OverrideReqEntityMapper mapper = new OverrideReqEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        OverrideRepositoryImpl overrideRepository = new OverrideRepositoryImpl(mapper, scheduler, service);
        OverrideUseCase overrideUseCase = new OverrideUseCase(overrideRepository);
        OverrideReq overrideReq = new OverrideReq();
        Single<BaseResponse> resp = overrideUseCase.execute(overrideReq);
        TestObserver<BaseResponse> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        Assert.assertEquals("00", testObserver.values().get(0).getErrorCode());

    }

    @Test
    public void T002_GetOverrideListUseCaseTest() {
        OverrideReqEntityMapper mapper = new OverrideReqEntityMapper();
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
        OverrideReqEntityMapper mapper = new OverrideReqEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        OverrideRepositoryImpl overrideRepository = new OverrideRepositoryImpl(mapper, scheduler, service);
        AcceptOverrideUseCase acceptOverrideUseCasetOverrideListUseCase = new  AcceptOverrideUseCase(overrideRepository);
        OverrideResp overrideResp = new OverrideResp();
        Single<BaseResponse> resp = acceptOverrideUseCasetOverrideListUseCase.execute(overrideResp);
        TestObserver<BaseResponse> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        Assert.assertEquals("00", testObserver.values().get(0).getErrorCode());

    }
}
