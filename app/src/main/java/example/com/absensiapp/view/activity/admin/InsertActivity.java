package example.com.absensiapp.view.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.ActivityInsertMemberBinding;
import example.com.absensiapp.model.BaseResponseModel;
import example.com.absensiapp.model.UserModel;
import example.com.absensiapp.view.utils.AeSimpleSHA1;
import example.com.absensiapp.view.listener.InsertListener;
import example.com.absensiapp.viewmodel.UserViewModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class InsertActivity extends AppCompatActivity implements InsertListener {

    private UserViewModel userViewModel = new UserViewModel();
    private ActivityInsertMemberBinding insertBinding;
    private AeSimpleSHA1 encrypt = new AeSimpleSHA1();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_member);

        insertBinding = DataBindingUtil.setContentView(this, R.layout.activity_insert_member);
        insertBinding.setClickListener(this);
        insertBinding.setUser(new UserModel());
        addUserObserver();
        setupUI(findViewById(android.R.id.content).getRootView());
    }

    public void addUserObserver() {
        userViewModel.getBaseResp().observe(this, new Observer<BaseResponseModel>() {
            @Override
            public void onChanged(BaseResponseModel baseResponseModel) {
                Toast.makeText(InsertActivity.this, baseResponseModel.getErrorMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), AdminBoardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onClickInsertListener(UserModel user) {
        insertBinding.getUser();
        if(checkIfFilled())
            Toast.makeText(this, "Fill All Field!", Toast.LENGTH_SHORT).show();
        else {
            try {
                user.setPassword(encrypt.SHA1(user.getPassword()));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.getMessage();
            }
            insertBinding.getUser();
            userViewModel.addUser(user);
        }

    }

    public void setupUI(View view) {
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    hideSoftKeyboard(InsertActivity.this);
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
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public boolean checkIfFilled() {
        return insertBinding.etUserId.getText().toString().matches("") || insertBinding.etPassword.getText().toString().matches("") || insertBinding.etName.getText().toString().matches("");
    }
}
