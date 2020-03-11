package example.com.domain.usecase.override;

import example.com.domain.model.BaseResponse;
import example.com.domain.repository.OverrideRepository;
import example.com.domain.usecase.SingleUseCaseWithParam;
import io.reactivex.Single;

public class DeletePendingOverrideUseCase implements SingleUseCaseWithParam<String, BaseResponse> {
    private final OverrideRepository overrideRepository;

    public DeletePendingOverrideUseCase(OverrideRepository overrideRepository) {
        this.overrideRepository = overrideRepository;
    }

    @Override
    public Single<BaseResponse> execute(String parameter) {
        return overrideRepository.doDeletePendingOverride(parameter);
    }
}
