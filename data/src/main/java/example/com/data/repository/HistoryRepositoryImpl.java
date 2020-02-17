package example.com.data.repository;

import example.com.data.entity.mapper.HistoryEntityMapper;
import example.com.data.net.Service;
import example.com.domain.model.InputHistory;
import example.com.domain.model.OutputHistory;
import example.com.domain.repository.HistoryRepository;
import io.reactivex.Scheduler;
import io.reactivex.Single;

public class HistoryRepositoryImpl implements HistoryRepository {

    private final HistoryEntityMapper mapper;
    private final Scheduler scheduler;
    private final Service service;

    public HistoryRepositoryImpl(HistoryEntityMapper mapper, Scheduler scheduler, Service service) {
        this.mapper = mapper;
        this.scheduler = scheduler;
        this.service = service;
    }

    @Override
    public Single<OutputHistory> doGetOutputHistory(InputHistory inputHistory) {

        return Single.defer(()->service.historyUser(mapper.inputHistoryToData(inputHistory)))
                .map(mapper::outputHistoryRespToDomain)
                .subscribeOn(scheduler);
    }
}
