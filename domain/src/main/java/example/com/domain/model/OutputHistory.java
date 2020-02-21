package example.com.domain.model;

import java.util.List;

import lombok.Data;

@Data
public class OutputHistory extends BaseResponse{

    private String outputUserId;
    private String outputMm;
    private String outputYyyy;
    private String outputAttend;
    private List<HistData> histData;
}