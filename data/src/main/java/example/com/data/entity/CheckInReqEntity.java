package example.com.data.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class CheckInReqEntity {
    @SerializedName("user_id")
    private String userId;
    private String state;
}
