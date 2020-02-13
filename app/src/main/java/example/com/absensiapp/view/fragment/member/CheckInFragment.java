package example.com.absensiapp.view.fragment.member;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.opencv.android.OpenCVLoader;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.FragmentCheckInBinding;
import example.com.absensiapp.model.BaseResponseModel;
import example.com.absensiapp.model.CheckInReqModel;
import example.com.absensiapp.view.activity.member.RecognitionActivity;
import example.com.absensiapp.view.listener.CheckInListener;
import example.com.absensiapp.viewmodel.UserViewModel;

public class CheckInFragment extends Fragment implements CheckInListener {

    private UserViewModel userViewModel = new UserViewModel();
    private String userId;
    private FragmentCheckInBinding checkInBinding;

    static {
        if (!OpenCVLoader.initDebug()) {
            Log.d("OpenCV", "OpenCV Not Loadede!");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        checkInBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_check_in, container, false);
        return checkInBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = this.requireActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        userViewModel = ViewModelProviders.of(requireActivity()).get(UserViewModel.class);
        checkInBinding.setCheck(new CheckInReqModel());
        checkInBinding.setOnClick(this);
        userId = sharedPreferences.getString("UserId", "");
        checkInBinding.tvUserId.setText(userId);
        checkObserver();
    }

    @Override
    public void onClickInButton(CheckInReqModel check) {
        check.setState("I");
        check.setUserId(userId);
        Intent intent = new Intent(requireActivity().getApplicationContext(), RecognitionActivity.class);
        intent.putExtra("checkModel", check);
        startActivity(intent);
    }

    @Override
    public void onClickOutButton(CheckInReqModel check) {
        check.setState("O");
        check.setUserId(userId);
        Intent intent = new Intent(requireActivity().getApplicationContext(), RecognitionActivity.class);
        intent.putExtra("checkModel", check);
        startActivity(intent);
    }

    private void checkObserver() {
        userViewModel.getBaseResp().observe(requireActivity(), new Observer<BaseResponseModel>() {
            @Override
            public void onChanged(BaseResponseModel baseResponseModel) {
            }
        });
    }
}
