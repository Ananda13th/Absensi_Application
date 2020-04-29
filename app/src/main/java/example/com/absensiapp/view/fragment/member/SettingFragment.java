package example.com.absensiapp.view.fragment.member;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import ch.zhaw.facerecognitionlibrary.Helpers.FileHelper;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.FramentSettingBinding;
import example.com.absensiapp.databinding.LayoutChangeProfileBinding;
import example.com.absensiapp.model.BaseResponseModel;
import example.com.absensiapp.model.UploadImageReqModel;
import example.com.absensiapp.model.UserModel;
import example.com.absensiapp.view.activity.member.InitDataTrainingActivity;
import example.com.absensiapp.view.utils.AeSimpleSHA1;
import example.com.absensiapp.view.listener.SettingListener;
import example.com.absensiapp.view.utils.CustomDialog;
import example.com.absensiapp.viewmodel.UserViewModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SettingFragment extends Fragment implements SettingListener {

    private FramentSettingBinding fragmentSettingBinding;
    private SharedPreferences sharedPreferences;
    private AlertDialog changePasswordDialog;
    private LayoutChangeProfileBinding changeProfileLayoutBinding;
    private UserViewModel userViewModel = new UserViewModel();
    private AeSimpleSHA1 encrypt = new AeSimpleSHA1();
    private String name;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentSettingBinding = DataBindingUtil.inflate(inflater, R.layout.frament_setting, container, false);
        changeProfileLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.layout_change_profile, container, false);
        return fragmentSettingBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("");
        sharedPreferences = this.requireActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("Name", "");
        fragmentSettingBinding.setOnClick(this);
        changeProfileLayoutBinding.setOnClick(this);
        UserModel userModel = new UserModel();
        userModel.setName(name);
        changeProfileLayoutBinding.setUser(userModel);

        final AlertDialog.Builder changePasswordBuilder = new AlertDialog.Builder(getActivity());
        changePasswordBuilder.setView(changeProfileLayoutBinding.getRoot());
        changePasswordDialog = changePasswordBuilder.create();
        changePasswordDialog.setTitle("Ubah Profil");

        userViewModel = ViewModelProviders.of(requireActivity()).get(UserViewModel.class);
        userObserver();
    }

    @Override
    public void onClickLogOutButton() {
        CustomDialog customDialog = new CustomDialog(this.getActivity());
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }

    @Override
    public void onClickChangePasswordButton() {

        changePasswordDialog.show();
    }

    @Override
    public void onClickSubmitButton(UserModel userModel) {
        if (checkIfFilled())
            Toast.makeText(requireActivity(), "Field Not Filled!", Toast.LENGTH_SHORT).show();
        else {
            userModel.setUserId(sharedPreferences.getString("UserId", ""));
            String name = sharedPreferences.getString("Name", "");
            try {
                userModel.setPassword(encrypt.SHA1(userModel.getPassword()));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.getMessage();
            }
            changeProfileLayoutBinding.getUser();
            FileHelper fh = new FileHelper();
            File file = new File(fh.TRAINING_PATH+name);
            Log.d("NAMA", file.toString());
            File destName = new File(fh.TRAINING_PATH+userModel.getName());
            if(file.exists()) {
                file.renameTo(destName);
                userViewModel.updateUser(userModel);
            }
        }
    }

    @Override
    public void onClickSyncButton() {
        Intent intent = new Intent(requireActivity().getApplicationContext(), InitDataTrainingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClickDownloadButton() {
        FileHelper fileHelper = new FileHelper();
        File path = new File(fileHelper.TRAINING_PATH+name);
        List<Bitmap> bitmapList = new ArrayList<>();
        if(path.exists()) {
            String[] fileNames = path.list();
            for (String fileName : fileNames) {
                Bitmap mBitmap = BitmapFactory.decodeFile(path.getPath() + "/" + fileName);
                Log.d("ImageDecode", mBitmap.toString());
                bitmapList.add(mBitmap);
            }
        }
        MultipartBody.Part[] multipartTypedOutput = new MultipartBody.Part[bitmapList.size()];

        for (int index = 0; index < bitmapList.size(); index++) {
            Bitmap bitmap = bitmapList.get(index);
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), String.valueOf(bitmap));
            multipartTypedOutput[index] = MultipartBody.Part.createFormData("imageFiles[]", String.valueOf(bitmap), surveyBody);
        }
        
        RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), name);
        UploadImageReqModel uploadImageReqModel = new UploadImageReqModel();
        uploadImageReqModel.setMultipart(multipartTypedOutput);
        uploadImageReqModel.setUserid(userid);
        Log.d("UPLOAD", uploadImageReqModel.toString());
        //userViewModel.uploadImage(uploadImageReqModel);

    }

    private void userObserver() {
        userViewModel.getBaseResp().observe(getViewLifecycleOwner(), new Observer<BaseResponseModel>() {
            @Override
            public void onChanged(BaseResponseModel baseResponseModel) {
                if(changePasswordDialog!=null)
                    changePasswordDialog.dismiss();
            }
        });
    }

    private boolean checkIfFilled() {
        return changeProfileLayoutBinding.etPassword.getText().toString().matches("");
    }
}
