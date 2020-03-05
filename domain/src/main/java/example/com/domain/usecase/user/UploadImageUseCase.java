package example.com.domain.usecase.user;

import example.com.domain.model.BaseResponse;
import example.com.domain.model.UploadImageReq;
import example.com.domain.repository.UserRepository;
import example.com.domain.usecase.SingleUseCaseWithParam;
import io.reactivex.Single;

public class UploadImageUseCase implements SingleUseCaseWithParam<UploadImageReq, BaseResponse> {

    private  final UserRepository userRepository;

    public UploadImageUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Single<BaseResponse> execute(UploadImageReq parameter) {
        return userRepository.doUploadImage(parameter.getUserid(), parameter.getMultipart());
    }
}
