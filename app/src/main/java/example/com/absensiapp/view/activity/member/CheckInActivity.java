package example.com.absensiapp.view.activity.member;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.ActivityCheckInBinding;
import example.com.absensiapp.model.BaseResponseModel;
import example.com.absensiapp.model.CheckInReqModel;
import example.com.absensiapp.view.activity.LoginActivity;
import example.com.absensiapp.view.activity.admin.AdminBoardActivity;
import example.com.absensiapp.view.listener.CheckInListener;
import example.com.absensiapp.viewmodel.UserViewModel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class CheckInActivity extends AppCompatActivity implements CheckInListener {

    private ActivityCheckInBinding checkInBinding;
    private UserViewModel userViewModel = new UserViewModel();
    private String userId;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        checkInBinding = DataBindingUtil.setContentView(this, R.layout.activity_check_in);
        checkInBinding.setOnClick(this);
        sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("UserId", "");
        checkInBinding.setCheck(new CheckInReqModel());
        Log.d("TET", userId);
        checkObserver();
    }

    @Override
    public void onClickInButton(CheckInReqModel check) {
        check.setState("In");
        check.setUserId(userId);
        userViewModel.checkInUser(check);
    }

    @Override
    public void onClickOutButton(CheckInReqModel check) {
        check.setState("Out");
        check.setUserId(userId);
        userViewModel.checkInUser(check);
    }

    @Override
    public void onClickLogOutButton() {
        sharedPreferences.edit().clear().apply();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void checkObserver() {
        userViewModel.getBaseResp().observe(this, new Observer<BaseResponseModel>() {
            @Override
            public void onChanged(BaseResponseModel baseResponseModel) {
                Toast.makeText(CheckInActivity.this, baseResponseModel.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
