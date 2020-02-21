package example.com.absensiapp.view.fragment.admin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import example.com.absensiapp.R;
import example.com.absensiapp.model.BaseResponseModel;
import example.com.absensiapp.model.UserListModel;
import example.com.absensiapp.view.adapter.UserAdapter;
import example.com.absensiapp.view.listener.UserRecycleListener;
import example.com.absensiapp.viewmodel.UserViewModel;
import lombok.SneakyThrows;

public class UserListFragment extends Fragment {

    private UserViewModel userViewModel = new UserViewModel();
    private UserAdapter userAdapter = new UserAdapter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @SneakyThrows
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUserList();
        setRecycleView();
        userAdapter.setOnClick(new UserRecycleListener() {
            @Override
            public void onClickDeleteButton(String userId) {
                deleteConfirmation(userId);
            }
        });
        deleteUserObserver();

    }

    private void deleteConfirmation(final String id) {
        AlertDialog deleteDialog = new AlertDialog.Builder(requireActivity())
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
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

    private void deleteUserObserver() {
        userViewModel.getBaseResp().observe(this, new Observer<BaseResponseModel>() {
            @Override
            public void onChanged(BaseResponseModel baseResponseModel) {
                Toast.makeText(requireActivity(), baseResponseModel.getErrorMessage(), Toast.LENGTH_SHORT).show();
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
