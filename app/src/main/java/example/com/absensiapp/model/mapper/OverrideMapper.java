package example.com.absensiapp.model.mapper;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import example.com.absensiapp.model.OverrideHistoryRespListModel;
import example.com.absensiapp.model.OverrideRespListModel;
import example.com.absensiapp.model.OverrideReqModel;
import example.com.absensiapp.model.OverrideRespModel;
import example.com.domain.model.OverrideHistoryRespList;
import example.com.domain.model.OverrideReq;
import example.com.domain.model.OverrideResp;
import example.com.domain.model.OverrideRespList;

public class OverrideMapper extends BaseResponseMapper{

    Gson gson = new Gson();

    public OverrideReq requestOverrideToDomain(OverrideReqModel overrideReqModel) {
        OverrideReq overrideReq = new OverrideReq();

        overrideReq.setAction(overrideReqModel.getAction());
        overrideReq.setDates(overrideReqModel.getDates());
        overrideReq.setTimes(overrideReqModel.getTimes());
        overrideReq.setUserId(overrideReqModel.getUserId());

        return overrideReq;
    }

    public OverrideResp acceptOverrideToDomain(OverrideRespModel overrideRespModel) {
        OverrideResp overrideResp = new OverrideResp();

        overrideResp.setId(overrideRespModel.getId());
        overrideResp.setAction(overrideRespModel.getAction());
        overrideResp.setDates(overrideRespModel.getDates());
        overrideResp.setTimes(overrideRespModel.getTimes());
        overrideResp.setUserId(overrideRespModel.getUserId());
        overrideResp.setStatus("Diterima");

        return overrideResp;
    }

    public OverrideResp rejectOverrideToDomain(OverrideRespModel overrideRespModel) {
        OverrideResp overrideResp = new OverrideResp();

        overrideResp.setId(overrideRespModel.getId());
        overrideResp.setAction(overrideRespModel.getAction());
        overrideResp.setDates(overrideRespModel.getDates());
        overrideResp.setTimes(overrideRespModel.getTimes());
        overrideResp.setUserId(overrideRespModel.getUserId());
        overrideResp.setStatus("Ditolak");

        return overrideResp;
    }

    public OverrideRespListModel getOverrideListToView(OverrideRespList overrideRespList) {
        OverrideRespListModel overrideRespListModel = new OverrideRespListModel();
        List<OverrideRespModel> overrideList= new ArrayList<>();

        overrideRespListModel.setErrorCode(overrideRespList.getErrorCode());
        overrideRespListModel.setErrorMessage(overrideRespList.getErrorMessage());

        for(int i = 0; i< overrideRespList.getOverrideList().size(); i++) {
            OverrideResp overrideResp = overrideRespList.getOverrideList().get(i);
            OverrideRespModel overrideRespModel = new OverrideRespModel();

            overrideRespModel.setId(overrideResp.getId());
            overrideRespModel.setUserId(overrideResp.getUserId());
            overrideRespModel.setName(overrideResp.getName());
            overrideRespModel.setTimes(overrideResp.getTimes());
            overrideRespModel.setDates(overrideResp.getDates());
            overrideRespModel.setAction(overrideResp.getAction());
            overrideRespModel.setStatus(overrideResp.getStatus());

            overrideList.add(overrideRespModel);
        }
        overrideRespListModel.setOverrideList(overrideList);
        return overrideRespListModel;
    }

    public OverrideHistoryRespListModel overrideHistoryToView(OverrideHistoryRespList overrideHistoryRespList) {
        String overrideHistoryRespListJson = gson.toJson(overrideHistoryRespList);
        OverrideHistoryRespListModel overrideHistoryRespListModel = gson.fromJson(overrideHistoryRespListJson,OverrideHistoryRespListModel.class);
        return overrideHistoryRespListModel;
    }
}
