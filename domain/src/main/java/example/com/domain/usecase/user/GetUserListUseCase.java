package example.com.domain.usecase.user;

import example.com.domain.model.UserList;
import example.com.domain.repository.UserRepository;
import example.com.domain.usecase.SingleUseCase;
import io.reactivex.Single;

public class GetUserListUseCase implements SingleUseCase<UserList> {

    private final UserRepository userRepository;

    public GetUserListUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Single<UserList> execute() {
        return userRepository.doGetListUser();
    }
}
