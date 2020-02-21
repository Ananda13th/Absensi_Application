package example.com.domain.repository;

import example.com.domain.model.BaseResponse;
import example.com.domain.model.OverrideReq;
import example.com.domain.model.OverrideResp;
import example.com.domain.model.OverrideRespList;
import io.reactivex.Single;

public interface OverrideRepository {

    Single<OverrideRespList> doGetOverrideList();
    Single<BaseResponse> doOverrideUser(OverrideReq overrideReq);
    Single<BaseResponse> doAcceptOverride(OverrideResp overrideResp);
}
