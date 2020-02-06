package example.com.domain.model;

import lombok.Data;

@Data
public class CheckInReq {
    private String userId;
    private String state;
}
