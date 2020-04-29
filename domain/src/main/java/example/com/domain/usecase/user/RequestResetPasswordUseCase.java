package example.com.domain.usecase.user;

import example.com.domain.model.BaseResponse;
import example.com.domain.model.ResetPasswordReq;
import example.com.domain.repository.UserRepository;
import example.com.domain.usecase.SingleUseCaseWithParam;
import io.reactivex.Single;

public class RequestResetPasswordUseCase implements SingleUseCaseWithParam<ResetPasswordReq, BaseResponse> {

    private final UserRepository userRepository;

    public RequestResetPasswordUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Single<BaseResponse> execute(ResetPasswordReq parameter) {
        return userRepository.doRequestResetPassword(parameter);
    }
}
