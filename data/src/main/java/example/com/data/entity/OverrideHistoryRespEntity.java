package example.com.data.entity;

import lombok.Data;

@Data
public class OverrideHistoryRespEntity {
    private String id;
    private String action;
    private String name;
    private String dates;
    private String times;
    private String status;
}
