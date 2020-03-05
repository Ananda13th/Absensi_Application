package example.com.domain.usecase.user;

import example.com.domain.model.BaseResponse;
import example.com.domain.model.User;
import example.com.domain.repository.UserRepository;
import example.com.domain.usecase.SingleUseCaseWithParam;
import io.reactivex.Single;

public class UpdateUserUseCase implements SingleUseCaseWithParam<User, BaseResponse> {
    private final UserRepository userRepository;

    public UpdateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Single<BaseResponse> execute(User parameter) {
        return userRepository.doUpdateUser(parameter.getUserId(), parameter);
    }
}
