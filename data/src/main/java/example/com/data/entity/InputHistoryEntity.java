package example.com.data.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class InputHistoryEntity {
    @SerializedName("user_id")
    private String userId;
    private String month;
    private String year;
}
