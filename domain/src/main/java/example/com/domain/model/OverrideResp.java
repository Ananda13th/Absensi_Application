package example.com.domain.model;

import lombok.Data;

@Data
public class OverrideResp {
    private int id;
    private String action;
    private String userId;
    private String date;
    private String time;
}
