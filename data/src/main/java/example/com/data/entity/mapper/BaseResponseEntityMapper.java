package example.com.data.entity.mapper;

import example.com.data.entity.BaseResponseEntity;
import example.com.domain.model.BaseResponse;

public class BaseResponseEntityMapper{

    public BaseResponse baseResponseToDomain(BaseResponseEntity baseResponseEntity) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setErrorCode(baseResponseEntity.getErrorCode());
        baseResponse.setErrorMessage(baseResponseEntity.getErrorMessage());
        return baseResponse;
    }
}
