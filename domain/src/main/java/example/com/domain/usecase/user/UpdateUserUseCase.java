package example.com.domain.usecase.user;

import example.com.domain.model.BaseResponse;
import example.com.domain.model.User;
import example.com.domain.repository.BaseResponseRepository;
import example.com.domain.usecase.SingleUseCaseWithParam;
import io.reactivex.Single;

public class UpdateUserUseCase implements SingleUseCaseWithParam<User, BaseResponse> {
    private final BaseResponseRepository baseResponseRepository;

    public UpdateUserUseCase(BaseResponseRepository baseResponseRepository) {
        this.baseResponseRepository = baseResponseRepository;
    }

    @Override
    public Single<BaseResponse> execute(User parameter) {
        return baseResponseRepository.doUpdateUser(parameter.getUserId(), parameter);
    }
}
