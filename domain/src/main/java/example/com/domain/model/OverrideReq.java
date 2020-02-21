package example.com.domain.model;

import lombok.Data;

@Data
public class OverrideReq {
    private String action;
    private String userId;
    private String date;
    private String time;
}
