package example.com.domain.usecase.user;

import example.com.domain.model.BaseResponse;
import example.com.domain.model.CheckInReq;
import example.com.domain.repository.UserRepository;
import example.com.domain.usecase.SingleUseCaseWithParam;
import io.reactivex.Single;

public class CheckInUseCase implements SingleUseCaseWithParam<CheckInReq, BaseResponse> {
    private final UserRepository userRepository;

    public CheckInUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Single<BaseResponse> execute(CheckInReq parameter) {
        return userRepository.doCheckUser(parameter);
    }
}
