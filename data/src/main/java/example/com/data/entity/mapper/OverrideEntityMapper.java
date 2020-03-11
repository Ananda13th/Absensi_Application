package example.com.data.entity.mapper;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import example.com.data.entity.OverrideHistoryRespListEntity;
import example.com.data.entity.OverrideReqEntity;
import example.com.data.entity.OverrideRespEntity;
import example.com.data.entity.OverrideRespListEntity;
import example.com.domain.model.OverrideHistoryRespList;
import example.com.domain.model.OverrideReq;
import example.com.domain.model.OverrideResp;
import example.com.domain.model.OverrideRespList;

public class OverrideEntityMapper extends BaseResponseEntityMapper {

    private Gson gson = new Gson();

    public OverrideReqEntity overrideReqToData(OverrideReq overrideReq) {
        OverrideReqEntity overrideReqEntity = new OverrideReqEntity();

        overrideReqEntity.setAction(overrideReq.getAction());
        overrideReqEntity.setDates(overrideReq.getDates());
        overrideReqEntity.setTimes(overrideReq.getTimes());
        overrideReqEntity.setUserId(overrideReq.getUserId());
        return overrideReqEntity;
    }

    public OverrideRespEntity acceptOverrideToData(OverrideResp overrideResp) {
        OverrideRespEntity overrideRespEntity = new OverrideRespEntity();

        overrideRespEntity.setId(overrideResp.getId());
        overrideRespEntity.setAction(overrideResp.getAction());
        overrideRespEntity.setDates(overrideResp.getDates());
        overrideRespEntity.setTimes(overrideResp.getTimes());
        overrideRespEntity.setUserId(overrideResp.getUserId());
        overrideRespEntity.setStatus("Diterima");
        return overrideRespEntity;
    }

    public OverrideRespEntity rejectOverrideToData(OverrideResp overrideResp) {
        OverrideRespEntity overrideRespEntity = new OverrideRespEntity();

        overrideRespEntity.setId(overrideResp.getId());
        overrideRespEntity.setAction(overrideResp.getAction());
        overrideRespEntity.setDates(overrideResp.getDates());
        overrideRespEntity.setTimes(overrideResp.getTimes());
        overrideRespEntity.setUserId(overrideResp.getUserId());
        overrideRespEntity.setStatus("Ditolak");
        return overrideRespEntity;
    }


    public OverrideRespList getOverrideListToDomain(OverrideRespListEntity overrideRespListEntity) {
        OverrideRespList overrideRespList = new OverrideRespList();
        List<OverrideResp> overrideList= new ArrayList<>();

        overrideRespList.setErrorCode(overrideRespListEntity.getErrorCode());
        overrideRespList.setErrorMessage(overrideRespListEntity.getErrorMessage());

        for(int i = 0; i< overrideRespListEntity.getOverrideList().size(); i++) {
            OverrideRespEntity overrideRespEntity = overrideRespListEntity.getOverrideList().get(i);
            OverrideResp overrideResp = new OverrideResp();

            overrideResp.setId(overrideRespEntity.getId());
            overrideResp.setUserId(overrideRespEntity.getUserId());
            overrideResp.setTimes(overrideRespEntity.getTimes());
            overrideResp.setDates(overrideRespEntity.getDates());
            overrideResp.setAction(overrideRespEntity.getAction());
            overrideResp.setStatus(overrideRespEntity.getStatus());

            overrideList.add(overrideResp);
        }
        overrideRespList.setOverrideList(overrideList);
        return overrideRespList;
    }

    public OverrideHistoryRespList overrideHistory(OverrideHistoryRespListEntity overrideHistoryRespListEntity) {
        String overrideHistoryRespListJson = gson.toJson(overrideHistoryRespListEntity);
        return gson.fromJson(overrideHistoryRespListJson,OverrideHistoryRespList.class);
    }

}
