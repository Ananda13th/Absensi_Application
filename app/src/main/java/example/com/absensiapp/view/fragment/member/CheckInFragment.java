package example.com.absensiapp.view.fragment.member;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.FragmentCheckInBinding;
import example.com.absensiapp.databinding.OverrideRequestLayoutBinding;
import example.com.absensiapp.model.BaseResponseModel;
import example.com.absensiapp.model.CheckInReqModel;
import example.com.absensiapp.model.OverrideReqModel;
import example.com.absensiapp.view.activity.member.RecognitionActivity;
import example.com.absensiapp.view.listener.CheckInListener;
import example.com.absensiapp.viewmodel.OverrideViewModel;
import example.com.absensiapp.viewmodel.UserViewModel;

public class CheckInFragment extends Fragment implements CheckInListener {

    private UserViewModel userViewModel = new UserViewModel();
    private OverrideViewModel overrideViewModel = new OverrideViewModel();
    private String userId;
    private FragmentCheckInBinding checkInBinding;
    private OverrideRequestLayoutBinding overrideBinding;
    private AlertDialog overrideDialog;


    static {
        if (!OpenCVLoader.initDebug()) {
            Log.d("OpenCV", "OpenCV Not Loadede!");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inisialisasi Binding Layout
        checkInBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_check_in, container, false);
        overrideBinding = DataBindingUtil.inflate(inflater, R.layout.override_request_layout, container, false);
        overrideBinding.setOnClick(this);
        checkInBinding.setOnClick(this);

        return checkInBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Ambil UserId Dari SharedPreferences
        SharedPreferences sharedPreferences = this.requireActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("UserId", "");
        //Inisialisasi ViewModel
        userViewModel = ViewModelProviders.of(requireActivity()).get(UserViewModel.class);
        overrideViewModel = ViewModelProviders.of(requireActivity()).get(OverrideViewModel.class);

        checkInBinding.setCheck(new CheckInReqModel());

        //Menampilkan DatePickerDialog
        overrideBinding.etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentDate=Calendar.getInstance();
                int mYear=mcurrentDate.get(Calendar.YEAR);
                int mMonth=mcurrentDate.get(Calendar.MONTH);
                int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(requireActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        overrideBinding.etDate.setText(selectedyear+"-"+selectedmonth+"-"+selectedday);
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();  }
        });

        //Menampilkan TimePicker
        overrideBinding.etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        overrideBinding.etTime.setText( String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        //Buat Object Baru Untuk Simpan UserId & Data Dari Spinner
        OverrideReqModel overrideReqModel = new OverrideReqModel();
        overrideReqModel.setUserId(userId);
        overrideReqModel.setAction(overrideBinding.spinnerStatus.getSelectedItem().toString());
        overrideBinding.setOverrideInput(overrideReqModel);
        initializeDialog();
        checkObserver();
        overrideObserver();
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

    @Override
    public void onClickOverrideButton() {
        overrideDialog.show();
    }

    @Override
    public void onClickRequestButton(OverrideReqModel overrideReqModel) {
        if(checkIfFilled())
            Toast.makeText(requireActivity(), "All Field Must Be Filled!", Toast.LENGTH_SHORT).show();
        else {
            overrideBinding.getOverrideInput();
            overrideViewModel.sendOverrideReq(overrideReqModel);
            overrideDialog.dismiss();
            overrideBinding.etTime.setText(null);
            overrideBinding.etDate.setText(null);
        }
    }

    private void checkObserver() {
        Activity activity = getActivity();
        userViewModel.getBaseResp().observe(requireActivity(), new Observer<BaseResponseModel>() {
            @Override
            public void onChanged(BaseResponseModel baseResponseModel) {
                Toast.makeText(activity, baseResponseModel.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void overrideObserver() {
       overrideViewModel.getBaseResp().observe(requireActivity(), new Observer<BaseResponseModel>() {
            @Override
            public void onChanged(BaseResponseModel baseResponseModel) {
                Toast.makeText(requireActivity(), baseResponseModel.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeDialog() {
        AlertDialog.Builder overrideBuilder = new AlertDialog.Builder(requireActivity());
        overrideBuilder.setView(overrideBinding.getRoot());
        overrideDialog = overrideBuilder.create();

    }

    public boolean checkIfFilled() {
        return overrideBinding.etTime.getText().toString().matches("") || overrideBinding.etDate.getText().toString().matches("");
    }

}
