package example.com.absensiapp.view.fragment.admin;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.LayoutUpdateUserBinding;
import example.com.absensiapp.model.BaseResponseModel;
import example.com.absensiapp.model.ResetPasswordRespListModel;
import example.com.absensiapp.model.ResetPasswordRespModel;
import example.com.absensiapp.model.UserModel;
import example.com.absensiapp.view.adapter.ResetPasswordAdapter;
import example.com.absensiapp.view.listener.ResetPasswordListener;
import example.com.absensiapp.view.listener.UserRecycleListener;
import example.com.absensiapp.view.utils.AeSimpleSHA1;
import example.com.absensiapp.viewmodel.UserViewModel;
import lombok.SneakyThrows;

public class ResetPasswordFragment extends Fragment{

    private UserViewModel userViewModel;
    private ResetPasswordAdapter resetPasswordAdapter = new ResetPasswordAdapter();
    private LayoutUpdateUserBinding updateUserLayoutBinding;
    private AlertDialog updatePassword;
    private boolean passwordOnly = true;
    private AeSimpleSHA1 encrypt = new AeSimpleSHA1();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        updateUserLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.layout_update_user, container, false);
        return inflater.inflate(R.layout.fragment_reset_password, container, false);
    }

    @SneakyThrows
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Daftar Reset Password");
        setRecycleView();
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        setList();
        final AlertDialog.Builder updateUserBuilder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        updateUserBuilder.setView(updateUserLayoutBinding.getRoot());
        updatePassword = updateUserBuilder.create();
        updateUserLayoutBinding.setOnClick(new UserRecycleListener() {
            @Override
            public void onClickDeleteButton(String userId) {

            }

            @Override
            public void onClickEditButton(UserModel userModel) {

            }

            @Override
            public void onClickSubmitButton(UserModel userModel) {
                try {
                    userModel.setPassword(encrypt.SHA1(userModel.getPassword()));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                userViewModel.updateUser(userModel);
                userViewModel.deleteRequestResetPassword(userModel.getUserId());
                updatePassword.dismiss();
            }
        });
        resetPasswordAdapter.setOnClick(new ResetPasswordListener() {
            @Override
            public void onClickCardview(ResetPasswordRespModel resetPasswordRespModel) {
                UserModel userModel = new UserModel();
                userModel.setName(resetPasswordRespModel.getName());
                userModel.setUserId(resetPasswordRespModel.getUserId());
                updateUserLayoutBinding.setUser(userModel);
                if(passwordOnly) {
                    updateUserLayoutBinding.etName.setEnabled(false);
                    updateUserLayoutBinding.etUserid.setEnabled(false);
                }
                updatePassword.show();
            }
        });
        observer();
    }

    private void setList() {
        userViewModel.getResetResp().observe(this, new Observer<ResetPasswordRespListModel>() {
            @Override
            public void onChanged(ResetPasswordRespListModel resetPasswordRespListModel) {
                resetPasswordAdapter.setResetList(resetPasswordRespListModel.getResetPassList());
            }
        });

    }

    private void observer() {
        userViewModel.getBaseResp().observe(requireActivity(), new Observer<BaseResponseModel>() {
            @Override
            public void onChanged(BaseResponseModel baseResponseModel) {
                Toast.makeText(getActivity(), baseResponseModel.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRecycleView() {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_view_reset_password);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(resetPasswordAdapter);
    }

}
