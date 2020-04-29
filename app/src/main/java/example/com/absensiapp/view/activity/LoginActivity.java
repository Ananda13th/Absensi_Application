package example.com.absensiapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.ActivityLoginBinding;
import example.com.absensiapp.databinding.LayoutRequestResetPasswordBinding;
import example.com.absensiapp.model.BaseResponseModel;
import example.com.absensiapp.model.ResetPasswordReqModel;
import example.com.absensiapp.model.UserModel;
import example.com.absensiapp.view.activity.admin.AdminBoardActivity;
import example.com.absensiapp.view.activity.member.MemberDashboardActivity;
import example.com.absensiapp.view.activity.member.TrainingDataActivity;
import example.com.absensiapp.view.listener.LoginListener;
import example.com.absensiapp.view.utils.AeSimpleSHA1;
import example.com.absensiapp.view.utils.PrefManager;
import example.com.absensiapp.viewmodel.UserViewModel;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    private UserViewModel userViewModel = new UserViewModel();
    private ActivityLoginBinding loginBinding;
    AlertDialog resetPasswordDialog;
    private LayoutRequestResetPasswordBinding resetPasswordBinding;
    private AeSimpleSHA1 encrypt = new AeSimpleSHA1();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        PreferenceManager.setDefaultValues(this, R.xml.mypreferences, false);

        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginBinding.setOnClick(this);
        loginBinding.setUser(new UserModel());

        resetPasswordBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_request_reset_password, null, false);
        resetPasswordBinding.setOnClick(this);
        resetPasswordBinding.setResetPass(new ResetPasswordReqModel());
        final AlertDialog.Builder resetPasswordBuilder = new AlertDialog.Builder(this);
        resetPasswordBuilder.setView(resetPasswordBinding.getRoot());
        resetPasswordDialog = resetPasswordBuilder.create();
        resetPasswordDialog.setTitle("Minta Ulang Password");
        loginBinding.tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPasswordDialog.show();
            }
        });

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
        baseResponseObserver();
        loginObserver();
        setupUI(findViewById(android.R.id.content).getRootView());
    }

    public void loginObserver() {
        userViewModel.getUser().observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                if(!userModel.getErrorCode().equals("00"))
                {
                    Toast.makeText(LoginActivity.this, userModel.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(userModel.getRole().matches("admin")) {
                        saveLoginDetails(userModel);
                        Intent intent = new Intent(getApplicationContext(), AdminBoardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if(userModel.getRole().matches("member"))
                    {
                        saveLoginDetails(userModel);
                        Intent intent = new Intent(getApplicationContext(), TrainingDataActivity.class);
                        intent.putExtra("Method",TrainingDataActivity.TIME);
                        intent.putExtra("Folder", "Training");
                        startActivity(intent);
                        finish();
                    }

                }
            }
        });
    }

    private void baseResponseObserver() {
        userViewModel.getBaseResp().observe(this, new Observer<BaseResponseModel>() {
            @Override
            public void onChanged(BaseResponseModel baseResponseModel) {
                Toast.makeText(getApplicationContext(), baseResponseModel.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCLickLoginButton(UserModel userModel) {
        if( checkIfFilled())
            Toast.makeText(this, "All Field Must Be Filled!", Toast.LENGTH_SHORT).show();
        else
        {
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

    }


    @Override
    public void onClickSubmitButton(ResetPasswordReqModel reqModel) {
        resetPasswordBinding.getResetPass();
        userViewModel.requestResetPassword(reqModel);
        resetPasswordDialog.dismiss();
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

    public void setupUI(View view) {
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    hideSoftKeyboard(LoginActivity.this);
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(
                Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0);
    }

    public boolean checkIfFilled() {
        return loginBinding.etUserid.getText().toString().matches("") || loginBinding.etPassword.getText().toString().matches("");
    }

}
