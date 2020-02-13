package example.com.absensiapp.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class CheckInReqModel implements Serializable {
    private String userId;
    private String state;
}
