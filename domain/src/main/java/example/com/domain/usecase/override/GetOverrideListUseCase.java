package example.com.domain.usecase.override;

import example.com.domain.model.OverrideRespList;
import example.com.domain.repository.OverrideRepository;
import example.com.domain.usecase.SingleUseCase;
import io.reactivex.Single;

public class GetOverrideListUseCase implements SingleUseCase<OverrideRespList> {
    private final OverrideRepository overrideRepository;

    public GetOverrideListUseCase(OverrideRepository overrideRepository) {
        this.overrideRepository = overrideRepository;
    }

    @Override
    public Single<OverrideRespList> execute() {
        return overrideRepository.doGetOverrideList();
    }
}
