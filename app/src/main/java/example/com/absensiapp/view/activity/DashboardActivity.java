package example.com.absensiapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.ActivityLoginBinding;
import example.com.absensiapp.model.UserModel;
import example.com.absensiapp.view.activity.admin.AdminBoardActivity;
import example.com.absensiapp.view.listener.LoginListener;
import example.com.absensiapp.viewmodel.UserViewModel;
import android.content.Intent;
import android.os.Bundle;

public class DashboardActivity extends AppCompatActivity implements LoginListener {

    private UserViewModel userViewModel = new UserViewModel();
    private ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginBinding.setOnClick(this);
        loginBinding.setUser(new UserModel());
        loginObserver();
    }

    public void loginObserver() {
        userViewModel.getuser().observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                if(userModel.getRole().equals("admin")) {
                    Intent intent = new Intent(getApplicationContext(), AdminBoardActivity.class);
                    intent.putExtra("LoginData", userModel.getUserId());
                    startActivity(intent);
                }
                else
                {
                    //Activity member
                }
            }
        });
    }

    @Override
    public void onCLickLoginButton(UserModel userModel) {
        loginBinding.getUser();
        userViewModel.login(userModel);
    }


}
