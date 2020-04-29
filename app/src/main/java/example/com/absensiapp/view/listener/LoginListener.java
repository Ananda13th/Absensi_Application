package example.com.absensiapp.view.listener;

import example.com.absensiapp.model.ResetPasswordReqModel;
import example.com.absensiapp.model.UserModel;

public interface LoginListener {

    void onCLickLoginButton(UserModel userModel);
    void onClickSubmitButton(ResetPasswordReqModel reqModel);
}
