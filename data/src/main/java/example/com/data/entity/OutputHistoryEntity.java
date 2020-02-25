package example.com.data.entity;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OutputHistoryEntity extends BaseResponseEntity {
    private String outputUserId;
    private String outputMm;
    private String outputYyyy;
    private String outputAttend;
    private List<HistDataEntity> histData;
}
