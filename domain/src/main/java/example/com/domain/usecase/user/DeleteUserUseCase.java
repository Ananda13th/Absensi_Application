package example.com.domain.usecase.user;

import example.com.domain.model.BaseResponse;
import example.com.domain.repository.UserRepository;
import example.com.domain.usecase.SingleUseCaseWithParam;
import io.reactivex.Single;

public class DeleteUserUseCase implements SingleUseCaseWithParam<String, BaseResponse> {

    private final UserRepository userRepository;

    public DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Single<BaseResponse> execute(String parameter) {
        return userRepository.doDeleteUser(parameter);
    }
}
