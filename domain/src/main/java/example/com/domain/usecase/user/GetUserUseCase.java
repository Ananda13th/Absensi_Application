package example.com.domain.usecase.user;

import example.com.domain.model.User;
import example.com.domain.repository.UserRepository;
import example.com.domain.usecase.SingleUseCaseWithParam;
import io.reactivex.Single;

public class GetUserUseCase implements SingleUseCaseWithParam<String,User> {
    private  final UserRepository userRepository;

    public GetUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Single<User> execute(String parameter) {
        return userRepository.doGetUser(parameter);
    }
}
