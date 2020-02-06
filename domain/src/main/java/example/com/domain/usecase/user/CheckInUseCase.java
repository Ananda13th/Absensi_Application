package example.com.domain.usecase.user;

import example.com.domain.model.BaseResponse;
import example.com.domain.model.CheckInReq;
import example.com.domain.repository.BaseResponseRepository;
import example.com.domain.usecase.SingleUseCaseWithParam;
import io.reactivex.Single;

public class CheckInUseCase implements SingleUseCaseWithParam<CheckInReq, BaseResponse> {
    private final BaseResponseRepository baseResponseRepository;

    public CheckInUseCase(BaseResponseRepository baseResponseRepository) {
        this.baseResponseRepository = baseResponseRepository;
    }

    @Override
    public Single<BaseResponse> execute(CheckInReq parameter) {
        return baseResponseRepository.doCheckUser(parameter);
    }
}
