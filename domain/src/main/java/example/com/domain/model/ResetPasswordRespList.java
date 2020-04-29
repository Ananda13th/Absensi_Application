package example.com.domain.model;

import java.util.List;

import lombok.Data;

@Data
public class ResetPasswordRespList extends BaseResponse {
    private List<ResetPasswordResp> resetPassList;
}
