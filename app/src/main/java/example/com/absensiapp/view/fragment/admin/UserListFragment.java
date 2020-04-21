package example.com.absensiapp.view.fragment.admin;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.UpdateUserLayoutBinding;
import example.com.absensiapp.model.BaseResponseModel;
import example.com.absensiapp.model.UserListModel;
import example.com.absensiapp.model.UserModel;
import example.com.absensiapp.view.adapter.UserAdapter;
import example.com.absensiapp.view.listener.UserRecycleListener;
import example.com.absensiapp.view.utils.AeSimpleSHA1;
import example.com.absensiapp.viewmodel.UserViewModel;
import lombok.SneakyThrows;

public class UserListFragment extends Fragment implements UserRecycleListener{

    private UserViewModel userViewModel = new UserViewModel();
    private UserAdapter userAdapter = new UserAdapter();
    private Context context;
    private AlertDialog updateUserDialog;
    private UpdateUserLayoutBinding updateUserLayoutBinding;
    private AeSimpleSHA1 encrypt = new AeSimpleSHA1();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        updateUserLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.update_user_layout, container, false);
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @SneakyThrows
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Daftar Member");
        context = getActivity();
        setUserList();
        setRecycleView();
        final AlertDialog.Builder updateUserBuilder = new AlertDialog.Builder(context);
        updateUserBuilder.setView(updateUserLayoutBinding.getRoot());
        updateUserDialog = updateUserBuilder.create();
        updateUserLayoutBinding.setOnClick(this);
        userAdapter.setOnClick(new UserRecycleListener() {
            @Override
            public void onClickDeleteButton(String userId) {
                deleteConfirmation(userId);
            }

            @Override
            public void onClickEditButton(UserModel userModel) {
                updateUserLayoutBinding.setUser(userModel);
                updateUserDialog.show();
            }

            @Override
            public void onClickSubmitButton(UserModel userModel) {
                //Dipanggil di bawah karena beda layout binding
            }
        });
        userObserver();
    }

    private void deleteConfirmation(final String id) {
        AlertDialog deleteDialog = new AlertDialog.Builder(requireActivity())
                .setTitle("Hapus Member")
                .setMessage("Anda Yakin Ingin Menghapus Member Ini?")
                .setPositiveButton("Hapus", (dialog, whichButton) -> userViewModel.deleteUser(id))
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        deleteDialog.show();
    }

    private void setUserList() {
        userViewModel.getRespUser().observe(this, new Observer<UserListModel>() {
            @Override
            public void onChanged(UserListModel userRespModel) {
                userAdapter.setUserList(userRespModel.getUserList());
            }
        });
    }

    private void userObserver() {
        userViewModel.getBaseResp().observe(getActivity(), new Observer<BaseResponseModel>() {
            @Override
            public void onChanged(BaseResponseModel baseResponseModel) {
                Toast.makeText(context, baseResponseModel.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRecycleView() {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_view_history);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);
    }

    private boolean checkIfFilled() {
        return updateUserLayoutBinding.etPassword.getText().toString().matches("") ||
                updateUserLayoutBinding.etName.getText().toString().matches("") ||
                updateUserLayoutBinding.etUserid.getText().toString().matches("");
    }

    @Override
    public void onClickDeleteButton(String userId) {
        //Sudah dipanggil di adapter setOnClick
        //Jangan dipakai karena beda layout, pasti null
    }

    @Override
    public void onClickEditButton(UserModel userModel) {
        //Sudah dipanggil di adapter setOnClick
        //Jangan dipakai karena beda layout, pasti null
    }

    @Override
    public void onClickSubmitButton(UserModel userModel) {
        String name = updateUserLayoutBinding.getUser().getName();
        if (checkIfFilled())
            Toast.makeText(requireActivity(), "Semua Bagian Belum Terisi!", Toast.LENGTH_SHORT).show();
        else {
            try {
                userModel.setPassword(encrypt.SHA1(userModel.getPassword()));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.getMessage();
            }
            updateUserLayoutBinding.getUser();
            userViewModel.updateUser(userModel);
            updateUserDialog.dismiss();
        }

    }
}
