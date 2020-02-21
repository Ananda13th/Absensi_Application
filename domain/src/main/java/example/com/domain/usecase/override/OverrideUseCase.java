package example.com.domain.usecase.override;

import example.com.domain.model.BaseResponse;
import example.com.domain.model.OverrideReq;
import example.com.domain.repository.OverrideRepository;
import example.com.domain.usecase.SingleUseCaseWithParam;
import io.reactivex.Single;

public class OverrideUseCase implements SingleUseCaseWithParam<OverrideReq, BaseResponse> {
    private final OverrideRepository overrideRepository;

    public OverrideUseCase(OverrideRepository overrideRepository) {
        this.overrideRepository = overrideRepository;
    }

    @Override
    public Single<BaseResponse> execute(OverrideReq parameter) {
        return overrideRepository.doOverrideUser(parameter);
    }
}
