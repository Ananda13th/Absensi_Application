package example.com.absensiapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.ActivityLoginBinding;
import example.com.absensiapp.model.UserModel;
import example.com.absensiapp.view.activity.admin.AdminBoardActivity;
import example.com.absensiapp.view.activity.member.CheckInActivity;
import example.com.absensiapp.view.listener.LoginListener;
import example.com.absensiapp.viewmodel.UserViewModel;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    private UserViewModel userViewModel = new UserViewModel();
    private ActivityLoginBinding loginBinding;
    private AeSimpleSHA1 encrypt = new AeSimpleSHA1();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        if(!new PrefManager(this).isUserLogedOut() && new PrefManager(this).isAdmin()) {
            Intent intent = new Intent(getApplicationContext(), AdminBoardActivity.class);
            startActivity(intent);
        }
        if(!new PrefManager(this).isUserLogedOut() && !new PrefManager(this).isAdmin()) {
            Intent intent = new Intent(getApplicationContext(), CheckInActivity.class);
            startActivity(intent);
        }
        loginBinding.setOnClick(this);
        loginBinding.setUser(new UserModel());
        loginObserver();
    }

    public void loginObserver() {
        userViewModel.getuser().observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                if(userModel.getRole().equals("admin")) {
                    saveLoginDetails(userModel);
                    Intent intent = new Intent(getApplicationContext(), AdminBoardActivity.class);
                    intent.putExtra("LoginData", userModel.getUserId());
                    startActivity(intent);
                    finish();
                }
                else
                {
                    saveLoginDetails(userModel);
                    Intent intent = new Intent(getApplicationContext(), CheckInActivity.class);
                    intent.putExtra("LoginData", userModel.getUserId());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onCLickLoginButton(UserModel userModel) {
        try {
            userModel.setPassword(encrypt.SHA1(userModel.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("TET", userModel.getPassword());
        loginBinding.getUser();
        userViewModel.login(userModel);
    }

    private void saveLoginDetails(UserModel userModel) {
        new PrefManager(this).saveLoginDetails(userModel);
    }

}
