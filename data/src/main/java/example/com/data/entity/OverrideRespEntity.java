package example.com.data.entity;

import lombok.Data;

@Data
public class OverrideRespEntity {
    private int id;
    private String action;
    private String userId;
    private String date;
    private String time;
}