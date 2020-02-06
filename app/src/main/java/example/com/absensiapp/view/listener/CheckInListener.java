package example.com.absensiapp.view.listener;

import example.com.absensiapp.model.CheckInReqModel;

public interface CheckInListener {
    void onClickInButton(CheckInReqModel check);
    void onClickOutButton(CheckInReqModel check);
    void onClickLogOutButton();
}
