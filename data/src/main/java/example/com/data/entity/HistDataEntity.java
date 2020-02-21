package example.com.data.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class HistDataEntity {
    @SerializedName("output_date")
    private String outputDate;
    @SerializedName("output_time_in")
    private String outputTimeIn;
    @SerializedName("output_time_out")
    private String outputTimeOut;
    @SerializedName("output_desc")
    private String outputDesc;
}
