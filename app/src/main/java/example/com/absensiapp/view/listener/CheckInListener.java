package example.com.absensiapp.view.listener;

import example.com.absensiapp.model.CheckInReqModel;
import example.com.absensiapp.model.OverrideReqModel;

public interface CheckInListener {
    void onClickInButton(CheckInReqModel check);
    void onClickOutButton(CheckInReqModel check);
    void onClickOverrideButton();
    void onClickRequestButton(OverrideReqModel overrideReqModel);
}
