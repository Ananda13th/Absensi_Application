package example.com.domain.usecase.user;

import example.com.domain.model.BaseResponse;
import example.com.domain.repository.BaseResponseRepository;
import example.com.domain.repository.UserRepository;
import example.com.domain.usecase.SingleUseCaseWithParam;
import io.reactivex.Single;

public class DeleteUserUseCase implements SingleUseCaseWithParam<String, BaseResponse> {

    private final BaseResponseRepository baseResponseRepository;

    public DeleteUserUseCase(BaseResponseRepository userRepository) {
        this.baseResponseRepository = userRepository;
    }

    @Override
    public Single<BaseResponse> execute(String parameter) {
        return baseResponseRepository.doDeleteUser(parameter);
    }
}
