package example.com.data.entity;

import java.util.List;

import lombok.Data;

@Data
public class OutputHistoryEntity extends BaseResponseEntity{

    private String outputUserId;
    private String outputMm;
    private String outputYyyy;
    private int outputAttend;
    private List<HistDataEntity> histData;
}
