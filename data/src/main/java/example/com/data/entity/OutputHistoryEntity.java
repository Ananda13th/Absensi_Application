package example.com.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OutputHistoryEntity extends BaseResponseEntity {
    @SerializedName("output_user_id")
    private String outputUserId;
    @SerializedName("output_mm")
    private String outputMm;
    @SerializedName("output_yyyy")
    private String outputYyyy;
    @SerializedName("output_attend")
    private String outputAttend;
    @SerializedName("hist_data")
    private List<HistDataEntity> histData;
}
