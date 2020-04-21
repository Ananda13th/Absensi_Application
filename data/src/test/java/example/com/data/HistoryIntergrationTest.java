package example.com.data;

import org.junit.Assert;
import org.junit.Test;

import example.com.data.entity.mapper.HistoryEntityMapper;
import example.com.data.net.Service;
import example.com.data.net.ServiceGenerator;
import example.com.data.repository.HistoryRepositoryImpl;
import example.com.domain.model.InputHistory;
import example.com.domain.model.OutputHistory;
import example.com.domain.usecase.history.SearchHistoryUseCase;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

public class HistoryIntergrationTest {
    @Test
    public void T001_SearchHistoryUseCaseTest() {
        HistoryEntityMapper mapper = new HistoryEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        HistoryRepositoryImpl historyRepository = new HistoryRepositoryImpl(mapper,scheduler,service);
        SearchHistoryUseCase historyUseCase = new SearchHistoryUseCase(historyRepository);
        InputHistory inputHistory = new InputHistory();
        Single<OutputHistory> resp = historyUseCase.execute(inputHistory);
        TestObserver<OutputHistory> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        Assert.assertEquals("00", testObserver.values().get(0).getErrorCode());
    }
}
