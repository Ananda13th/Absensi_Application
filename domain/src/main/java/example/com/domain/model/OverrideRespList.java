package example.com.domain.model;

import java.util.List;

import lombok.Data;

@Data
public class OverrideRespList extends BaseResponse{

    private List<OverrideResp> overrideList;
}
