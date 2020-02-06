package example.com.absensiapp.model;

import lombok.Data;

@Data
public class HistoryRespModel extends BaseResponseModel{
    private String userId;
    private String name;
    private String month;
    private String attend;
}
