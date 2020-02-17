package example.com.absensiapp.view.fragment.member;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.ChangePasswordLayoutBinding;
import example.com.absensiapp.databinding.FragmentSettingBinding;
import example.com.absensiapp.model.BaseResponseModel;
import example.com.absensiapp.model.UserModel;
import example.com.absensiapp.view.activity.member.AddPersonFormActivity;
import example.com.absensiapp.view.activity.member.InitDataTrainingActivity;
import example.com.absensiapp.view.activity.member.TrainingDataActivity;
import example.com.absensiapp.view.utils.AeSimpleSHA1;
import example.com.absensiapp.view.activity.LoginActivity;
import example.com.absensiapp.view.listener.SettingListener;
import example.com.absensiapp.view.utils.CustomDialog;
import example.com.absensiapp.viewmodel.UserViewModel;

public class SettingFragment extends Fragment implements SettingListener {

    private FragmentSettingBinding fragmentSettingBinding;
    private SharedPreferences sharedPreferences;
    private AlertDialog changePasswordDialog;
    private ChangePasswordLayoutBinding changePasswordLayoutBinding;
    private UserViewModel userViewModel = new UserViewModel();
    private AeSimpleSHA1 encrypt = new AeSimpleSHA1();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentSettingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
        changePasswordLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.change_password_layout, container, false);
        return fragmentSettingBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        sharedPreferences = this.getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        fragmentSettingBinding.setOnClick(this);
        changePasswordLayoutBinding.setOnClick(this);
        changePasswordLayoutBinding.setUser(new UserModel());
        super.onViewCreated(view, savedInstanceState);
        userViewModel = ViewModelProviders.of(requireActivity()).get(UserViewModel.class);
        changePasswordObserver();
    }

    @Override
    public void onClickLogOutButton() {
        CustomDialog customDialog = new CustomDialog(this.getActivity());
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }

    @Override
    public void onClickChangePasswordButton() {
        final AlertDialog.Builder changePasswordBuilder = new AlertDialog.Builder(getActivity());
        changePasswordBuilder.setView(changePasswordLayoutBinding.getRoot());
        changePasswordDialog = changePasswordBuilder.create();
        changePasswordDialog.setTitle("CHANGE PASSWORD");
        changePasswordDialog.show();
    }

    @Override
    public void onClickSubmitButton(UserModel userModel) {
        userModel.setUserId(sharedPreferences.getString("UserId", ""));
        try {
            userModel.setPassword(encrypt.SHA1(userModel.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        changePasswordLayoutBinding.getUser();
        Log.d("TEST", userModel.toString());
        userViewModel.updateUser(userModel);
    }

    @Override
    public void onClickSyncButton() {
        Intent intent = new Intent(getActivity().getApplicationContext(), InitDataTrainingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClickTrainButton() {
        Intent intent = new Intent(getActivity().getApplicationContext(), AddPersonFormActivity.class);
        startActivity(intent);
    }

    private void changePasswordObserver() {
        userViewModel.getBaseResp().observe(requireActivity(), new Observer<BaseResponseModel>() {
            @Override
            public void onChanged(BaseResponseModel baseResponseModel) {
                changePasswordDialog.dismiss();
                Toast.makeText(getActivity(), baseResponseModel.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
