package example.com.absensiapp.view.fragment.admin;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import example.com.absensiapp.viewmodel.UserViewModel;
import lombok.SneakyThrows;

public class UserListFragment extends Fragment {

    private UserViewModel userViewModel = new UserViewModel();
    private UserAdapter userAdapter = new UserAdapter();
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        UpdateUserLayoutBinding updateUserLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.update_user_layout, container, false);
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
        userAdapter.setOnClick(new UserRecycleListener() {
            @Override
            public void onClickDeleteButton(String userId) {
                deleteConfirmation(userId);
            }

            @Override
            public void onClickEditButton(UserModel userModel) {
                final android.app.AlertDialog.Builder updateUserBuilder = new android.app.AlertDialog.Builder(getActivity());
                updateUserBuilder.setView(updateUserLayoutBinding.getRoot());
                updateUserLayoutBinding = updateUserBuilder.create();
                updateUserLayoutBinding.setTitle("CHANGE PASSWORD");
                updateUserLayoutBinding.show();
            }
        });
        userObserver();

    }

    private void deleteConfirmation(final String id) {
        AlertDialog deleteDialog = new AlertDialog.Builder(requireActivity())
                .setTitle("Delete")
                .setMessage("Do you want to delete selected member?")
                .setPositiveButton("Delete", (dialog, whichButton) -> userViewModel.deleteUser(id))
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
        userViewModel.getBaseResp().observe(this, new Observer<BaseResponseModel>() {
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

}
