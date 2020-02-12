package example.com.absensiapp.view.listener;

import example.com.absensiapp.model.UserModel;

public interface SettingListener {
    void onClickLogOutButton();
    void onClickChangePasswordButton();
    void onClickSubmitButton(UserModel userModel);
    void onClickTrainButton();
}
