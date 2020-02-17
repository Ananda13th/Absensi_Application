package example.com.domain.usecase.user;

import example.com.domain.model.InputHistory;
import example.com.domain.model.OutputHistory;
import example.com.domain.repository.HistoryRepository;
import example.com.domain.usecase.SingleUseCaseWithParam;
import io.reactivex.Single;

public class SearchHistoryUseCase implements SingleUseCaseWithParam<InputHistory, OutputHistory> {
    private final HistoryRepository historyRepository;

    public SearchHistoryUseCase(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public Single<OutputHistory> execute(InputHistory parameter) {
        return historyRepository.doGetOutputHistory(parameter);
    }
}
