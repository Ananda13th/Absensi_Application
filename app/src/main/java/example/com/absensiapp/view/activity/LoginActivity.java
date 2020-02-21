package example.com.absensiapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.ActivityLoginBinding;
import example.com.absensiapp.model.UserModel;
import example.com.absensiapp.view.activity.admin.AdminBoardActivity;
import example.com.absensiapp.view.activity.member.MemberDashboardActivity;
import example.com.absensiapp.view.activity.member.TrainingDataActivity;
import example.com.absensiapp.view.listener.LoginListener;
import example.com.absensiapp.view.utils.AeSimpleSHA1;
import example.com.absensiapp.view.utils.PrefManager;
import example.com.absensiapp.viewmodel.UserViewModel;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    private UserViewModel userViewModel = new UserViewModel();
    private ActivityLoginBinding loginBinding;
    private AeSimpleSHA1 encrypt = new AeSimpleSHA1();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        setContentView(R.layout.activity_login);
        PreferenceManager.setDefaultValues(this, R.xml.mypreferences, false);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        if(!new PrefManager(this).isUserLogedOut() && new PrefManager(this).isAdmin()) {
            Intent intent = new Intent(getApplicationContext(), AdminBoardActivity.class);
            startActivity(intent);
            finish();
        }
        if(!new PrefManager(this).isUserLogedOut() && !new PrefManager(this).isAdmin()) {
            Intent intent = new Intent(getApplicationContext(), MemberDashboardActivity.class);
            startActivity(intent);
            finish();
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
                    startActivity(intent);
                    finish();
                }
                else
                {
                    saveLoginDetails(userModel);
                    Intent intent = new Intent(getApplicationContext(), TrainingDataActivity.class);
                    intent.putExtra("Method",TrainingDataActivity.TIME);
                    intent.putExtra("Folder", "Training");
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
            e.getMessage();
        }
        loginBinding.getUser();
        userViewModel.login(userModel);
    }

    private void saveLoginDetails(UserModel userModel) {
        new PrefManager(this).saveLoginDetails(userModel);
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void checkPermission() {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.INTERNET
        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }


}
