package example.com.absensiapp.model.mapper;

import java.util.ArrayList;
import java.util.List;

import example.com.absensiapp.model.OverrideRespListModel;
import example.com.absensiapp.model.OverrideReqModel;
import example.com.absensiapp.model.OverrideRespModel;
import example.com.domain.model.OverrideReq;
import example.com.domain.model.OverrideResp;
import example.com.domain.model.OverrideRespList;

public class OverrideMapper extends BaseResponseMapper{

    public OverrideReq acceptOverrideToDomain(OverrideReqModel overrideReqModel) {
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
            overrideRespModel.setTimes(overrideResp.getTimes());
            overrideRespModel.setDates(overrideResp.getDates());
            overrideRespModel.setAction(overrideResp.getAction());

            overrideList.add(overrideRespModel);
        }
        overrideRespListModel.setOverrideList(overrideList);
        return overrideRespListModel;
    }
}
