package example.com.absensiapp.model;

import java.util.List;

import example.com.domain.model.BaseResponse;
import lombok.Data;

@Data
public class ResetPasswordRespListModel extends BaseResponse {
    private List<ResetPasswordRespModel> resetPassList;
}
