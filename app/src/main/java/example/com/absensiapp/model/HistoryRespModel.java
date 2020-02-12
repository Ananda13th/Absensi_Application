package example.com.absensiapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class HistoryRespModel extends BaseResponseModel{
    private String userId;
    private String name;
    private String month;
    private String attend;
}
