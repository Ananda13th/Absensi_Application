package example.com.absensiapp.model.mapper;


import java.util.ArrayList;
import java.util.List;

import example.com.absensiapp.model.HistDataModel;
import example.com.absensiapp.model.InputHistoryModel;
import example.com.absensiapp.model.OutputHistoryModel;
import example.com.domain.model.HistData;
import example.com.domain.model.InputHistory;
import example.com.domain.model.OutputHistory;

public class HistoryMapper extends BaseResponseMapper{
    public OutputHistoryModel outputHistoryRespToView(OutputHistory outputHistory) {

        OutputHistoryModel historyModel = new OutputHistoryModel();
        List<HistDataModel> outputHistoryList = new ArrayList<>();

        historyModel.setErrorCode(outputHistory.getErrorCode());
        historyModel.setErrorMessage(outputHistory.getErrorMessage());
        historyModel.setOutputAttend(outputHistory.getOutputAttend());
        historyModel.setOutputMm(outputHistory.getOutputMm());
        historyModel.setOutputYyyy(outputHistory.getOutputYyyy());
        historyModel.setOutputUserId(outputHistory.getOutputUserId());

        for(int i=0;i<outputHistory.getHistData().size();i++) {

            HistData histData = outputHistory.getHistData().get(i);
            HistDataModel histDataModel = new HistDataModel();

            histDataModel.setOutputDate(histData.getOutputDate());
            histDataModel.setOutputDesc(histData.getOutputDesc());
            histDataModel.setOutputTimeIn(histData.getOutputTimeIn());
            histDataModel.setOutputTimeOut(histData.getOutputTimeOut());

            outputHistoryList.add(histDataModel);
        }

        historyModel.setHistData(outputHistoryList);
        return historyModel;
    }

    public InputHistory inputHistoryToDomain(InputHistoryModel inputHistoryModel) {

        InputHistory inputHistory = new InputHistory();

        inputHistory.setMonth(inputHistoryModel.getMonth());
        inputHistory.setYear(inputHistoryModel.getYear());
        inputHistory.setUserId(inputHistoryModel.getUserId());

        return inputHistory;
    }
}
