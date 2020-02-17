package example.com.domain.model;

import lombok.Data;

@Data
public class InputHistory extends BaseResponse {
    private String userId;
    private String month;
    private String year;
}
