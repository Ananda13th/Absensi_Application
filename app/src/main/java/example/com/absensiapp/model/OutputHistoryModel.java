package example.com.absensiapp.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OutputHistoryModel extends BaseResponseModel{
    private String outputUserId;
    private String outputMm;
    private String outputYyyy;
    private int outputAttend;
    private List<HistDataModel> histData;
}
