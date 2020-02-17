package example.com.domain.repository;

import example.com.domain.model.InputHistory;
import example.com.domain.model.OutputHistory;
import io.reactivex.Single;

public interface HistoryRepository {

    Single<OutputHistory> doGetOutputHistory(InputHistory inputHistory);
}
