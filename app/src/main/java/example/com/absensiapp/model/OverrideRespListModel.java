package example.com.absensiapp.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OverrideRespListModel extends BaseResponseModel{
    private List<OverrideRespModel> overrideList;
}
