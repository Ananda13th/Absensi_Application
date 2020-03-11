package example.com.domain.model;

import lombok.Data;

@Data
public class OverrideHistoryResp {
    private String id;
    private String action;
    private String dates;
    private String times;
    private String status;
}
