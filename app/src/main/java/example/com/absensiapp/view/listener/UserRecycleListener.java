package example.com.absensiapp.view.listener;

import example.com.absensiapp.model.UserModel;

public interface UserRecycleListener {

    void onClickDeleteButton(String userId);
    void onClickEditButton(UserModel userModel);
}
