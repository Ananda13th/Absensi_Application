package example.com.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OverrideRespListEntity extends BaseResponseEntity {

    @SerializedName("override_list")
    private List<OverrideRespEntity> overrideList;
}
