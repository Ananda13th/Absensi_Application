package example.com.domain.usecase.override;

import example.com.domain.model.BaseResponse;
import example.com.domain.model.OverrideResp;
import example.com.domain.repository.OverrideRepository;
import example.com.domain.usecase.SingleUseCaseWithParam;
import io.reactivex.Single;

public class RejectOverrideUseCase implements SingleUseCaseWithParam<OverrideResp, BaseResponse> {

    private final OverrideRepository overrideRepository;

    public RejectOverrideUseCase(OverrideRepository overrideRepository) {
        this.overrideRepository = overrideRepository;
    }

    @Override
    public Single<BaseResponse> execute(OverrideResp parameter) {
        return overrideRepository.doRejectOverride(parameter);
    }
}
