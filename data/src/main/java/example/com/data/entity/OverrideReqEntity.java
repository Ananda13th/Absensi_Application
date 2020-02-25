package example.com.data.entity;

import lombok.Data;

@Data
public class OverrideReqEntity {
    private String action;
    private String userId;
    private String date;
    private String time;
}
