package example.com.absensiapp.view.activity.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import example.com.absensiapp.R;
import example.com.absensiapp.model.BaseResponseModel;
import example.com.absensiapp.model.UserModel;
import example.com.absensiapp.view.adapter.UserAdapter;
import example.com.absensiapp.view.listener.RecycleListener;
import example.com.absensiapp.viewmodel.UserViewModel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdminBoardActivity extends AppCompatActivity{

    private UserViewModel userViewModel = new UserViewModel();
    private UserAdapter adapter = new UserAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        FloatingActionButton addButton = findViewById(R.id.fab_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToInsertActivity = new Intent(getApplicationContext(), InsertActivity.class);
                startActivity(goToInsertActivity);
            }
        });
        setUserList();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(new RecycleListener() {
            @Override
            public void onClickCardView(UserModel userModel) {
            }

            @Override
            public void onClickDeleteButton(String userId) {
                deleteConfirmation(userId);
            }

            @Override
            public void onClickUpdateButton(UserModel userModel) {

            }
        });
        deleteUserObserver();
    }

    private void setUserList() {

        userViewModel.getRespUser().observe(this, userRespModel -> adapter.setUserList(userRespModel.getUserList()));
    }

    private void deleteConfirmation(final String id) {
        AlertDialog deleteDialog = new AlertDialog.Builder(this)
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

    private void deleteUserObserver() {
        userViewModel.getBaseResp().observe(this, new Observer<BaseResponseModel>() {
            @Override
            public void onChanged(BaseResponseModel baseResponseModel) {
                Toast.makeText(AdminBoardActivity.this, baseResponseModel.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
