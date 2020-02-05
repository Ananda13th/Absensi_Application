package example.com.domain.usecase.user;

import example.com.domain.model.User;
import example.com.domain.repository.UserRepository;
import example.com.domain.usecase.SingleUseCaseWithParam;
import io.reactivex.Single;

public class LoginUseCase implements SingleUseCaseWithParam<User,User> {
    private final UserRepository userRepository;

    public LoginUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Single<User> execute(User parameter) {
        return userRepository.doLogin(parameter);
    }
}
