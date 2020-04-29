package example.com.domain.usecase.user;

import example.com.domain.model.ResetPasswordRespList;
import example.com.domain.repository.UserRepository;
import example.com.domain.usecase.SingleUseCase;
import io.reactivex.Single;

public class GetResetPasswordListUseCase implements SingleUseCase<ResetPasswordRespList> {

    private final UserRepository userRepository;

    public GetResetPasswordListUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Single<ResetPasswordRespList> execute() {
        return userRepository.doGetResetPasswordList();
    }
}
