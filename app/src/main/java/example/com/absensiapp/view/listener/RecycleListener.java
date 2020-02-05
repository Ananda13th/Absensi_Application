package example.com.absensiapp.view.listener;

import example.com.absensiapp.model.UserModel;

public interface RecycleListener {

    void onClickCardView(UserModel userModel);
    void onClickDeleteButton(String userId);
    void onClickUpdateButton(UserModel userModel);
}
