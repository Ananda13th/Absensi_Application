package example.com.data.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import example.com.data.entity.HistDataEntity;
import example.com.data.entity.InputHistoryEntity;
import example.com.data.entity.OutputHistoryEntity;
import example.com.domain.model.HistData;
import example.com.domain.model.InputHistory;
import example.com.domain.model.OutputHistory;

public class HistoryEntityMapper {

    public OutputHistory outputHistoryRespToDomain(OutputHistoryEntity outputHistoryEntity) {

        OutputHistory outputHistory = new OutputHistory();
        List<HistData> outputHistoryList = new ArrayList<>();

        outputHistory.setErrorCode(outputHistoryEntity.getErrorCode());
        outputHistory.setErrorMessage(outputHistoryEntity.getErrorMessage());
        outputHistory.setOutputAttend(outputHistoryEntity.getOutputAttend());
        outputHistory.setOutputMm(outputHistoryEntity.getOutputMm());
        outputHistory.setOutputYyyy(outputHistoryEntity.getOutputYyyy());
        outputHistory.setOutputUserId(outputHistoryEntity.getOutputUserId());

        for(int i=0;i<outputHistoryEntity.getHistData().size();i++) {

            HistDataEntity histDataEntity = outputHistoryEntity.getHistData().get(i);
            HistData histData = new HistData();

            histData.setOutputDate(histDataEntity.getOutputDate());
            histData.setOutputDesc(histDataEntity.getOutputDesc());
            histData.setOutputTimeIn(histDataEntity.getOutputTimeIn());
            histData.setOutputTimeOut(histDataEntity.getOutputTimeOut());

            outputHistoryList.add(histData);
        }

        outputHistory.setHistData(outputHistoryList);
        return outputHistory;
    }

    public InputHistoryEntity inputHistoryToData(InputHistory inputHistory) {

        InputHistoryEntity inputHistoryEntity = new InputHistoryEntity();

        inputHistoryEntity.setMonth(inputHistory.getMonth());
        inputHistoryEntity.setYear(inputHistory.getYear());
        inputHistoryEntity.setUserId(inputHistory.getUserId());

        return inputHistoryEntity;
    }
}
