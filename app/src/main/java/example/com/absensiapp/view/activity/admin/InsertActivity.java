package example.com.absensiapp.view.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.ActivityInsertBinding;
import example.com.absensiapp.model.BaseResponseModel;
import example.com.absensiapp.model.UserModel;
import example.com.absensiapp.view.utils.AeSimpleSHA1;
import example.com.absensiapp.view.listener.InsertListener;
import example.com.absensiapp.viewmodel.UserViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class InsertActivity extends AppCompatActivity implements InsertListener {

    private UserViewModel userViewModel = new UserViewModel();
    private ActivityInsertBinding insertBinding;
    private AeSimpleSHA1 encrypt = new AeSimpleSHA1();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        insertBinding = DataBindingUtil.setContentView(this, R.layout.activity_insert);
        insertBinding.setClickListener(this);
        insertBinding.setUser(new UserModel());
        addUserObserver();
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
        try {
            user.setPassword(encrypt.SHA1(user.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.getMessage();
        }
        insertBinding.getUser();
        Log.d("TET", user.toString());
        userViewModel.addUser(user);
    }


}
