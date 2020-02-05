package example.com.absensiapp.model.mapper;

import example.com.absensiapp.model.BaseResponseModel;
import example.com.domain.model.BaseResponse;

public class BaseResponseMapper {
    public BaseResponseModel baseResponseToView(BaseResponse baseResponseFromDomain) {
        BaseResponseModel baseResponseModel = new BaseResponseModel();
        baseResponseModel.setErrorCode(baseResponseFromDomain.getErrorCode());
        baseResponseModel.setErrorMessage(baseResponseFromDomain.getErrorMessage());
        return baseResponseModel;
    }
}
