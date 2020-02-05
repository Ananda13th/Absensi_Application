package example.com.domain.usecase.user;

import example.com.domain.model.UserResp;
import example.com.domain.repository.UserRepository;
import example.com.domain.usecase.SingleUseCase;
import io.reactivex.Single;

public class GetUserListUseCase implements SingleUseCase<UserResp> {

    private final UserRepository userRepository;

    public GetUserListUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Single<UserResp> execute() {
        return userRepository.doGetListUser();
    }
}
