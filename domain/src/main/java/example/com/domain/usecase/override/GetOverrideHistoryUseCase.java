package example.com.domain.usecase.override;

import example.com.domain.model.OverrideHistoryRespList;
import example.com.domain.repository.OverrideRepository;
import example.com.domain.usecase.SingleUseCaseWithParam;
import io.reactivex.Single;

public class GetOverrideHistoryUseCase implements SingleUseCaseWithParam<String, OverrideHistoryRespList> {

    private final OverrideRepository overrideRepository;

    public GetOverrideHistoryUseCase(OverrideRepository overrideRepository) {
        this.overrideRepository = overrideRepository;
    }

    @Override
    public Single<OverrideHistoryRespList> execute(String parameter) {
        return overrideRepository.doGetOverrideHistoryList(parameter);
    }
}
