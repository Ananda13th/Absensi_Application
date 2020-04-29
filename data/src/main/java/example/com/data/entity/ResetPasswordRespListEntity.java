package example.com.data.entity;

import java.util.List;

import lombok.Data;

@Data
public class ResetPasswordRespListEntity extends BaseResponseEntity {

    private List<ResetPasswordRespEntity> resetPassList;
}
