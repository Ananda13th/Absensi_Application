package example.com.data.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class OverrideRespEntity {
    private int id;
    private String action;
    @SerializedName("user_id")
    private String userId;
    private String date;
    private String time;
}
