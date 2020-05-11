package example.com.absensiapp.view.activity.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import example.com.absensiapp.R;
import example.com.absensiapp.model.OverrideRespListModel;
import example.com.absensiapp.model.ResetPasswordRespListModel;
import example.com.absensiapp.view.fragment.admin.OverrideFragment;
import example.com.absensiapp.view.fragment.admin.ResetPasswordFragment;
import example.com.absensiapp.view.fragment.admin.UserListFragment;
import example.com.absensiapp.view.utils.CustomDialog;
import example.com.absensiapp.viewmodel.OverrideViewModel;
import example.com.absensiapp.viewmodel.UserViewModel;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdminBoardActivity extends AppCompatActivity {

    private Fragment fragment;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        setBottomNavigation();
        FloatingActionButton addButton = findViewById(R.id.fab_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToInsertActivity = new Intent(getApplicationContext(), InsertActivity.class);
                startActivity(goToInsertActivity);
            }
        });
        fragment = new UserListFragment();
        fragmentManager.beginTransaction().replace(R.id.fragment_layout, fragment).commit();
    }

    private void setBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_override :
                        fragment = new OverrideFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragment_layout, fragment).commit();
                        break;
                    case R.id.navigation_user:
                        fragment = new UserListFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragment_layout, fragment).commit();
                        break;
                    case R.id.navigation_log_out:
                        CustomDialog customDialog = new CustomDialog(AdminBoardActivity.this);
                        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        customDialog.show();
                    case R.id.navigation_password:
                        fragment = new ResetPasswordFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragment_layout, fragment).commit();
                        break;
                }
                return false;
            }
        });
        int overrideMenuId = bottomNavigationView.getMenu().getItem(0).getItemId();
        int resetMenuId = bottomNavigationView.getMenu().getItem(1).getItemId();
        OverrideViewModel overrideViewModel = ViewModelProviders.of(this).get(OverrideViewModel.class);
        overrideViewModel.getOverrideList().observe(this, new Observer<OverrideRespListModel>() {
            @Override
            public void onChanged(OverrideRespListModel overrideRespListModel) {
                int overrideListNumber = 0;
                for(int i=0;i<overrideRespListModel.getOverrideList().size();i++) {
                    if(overrideRespListModel.getOverrideList().get(i).getStatus().equals("Diproses"))
                        overrideListNumber = overrideListNumber + 1;
                    }
                    if(overrideListNumber != 0)
                    {
                        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(overrideMenuId);
                        badge.setNumber(overrideListNumber);
                    }
                    else
                    {
                        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(overrideMenuId);
                        badge.setVisible(false);
                    }
            }
        });
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getResetResp().observe(this, new Observer<ResetPasswordRespListModel>() {
            @Override
            public void onChanged(ResetPasswordRespListModel resetPasswordRespListModel) {
                int resetListNumber = resetPasswordRespListModel.getResetPassList().size();
                if(resetListNumber != 0)
                {
                    BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(resetMenuId);
                    badge.setNumber(resetListNumber);
                }
                else
                {
                    BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(resetMenuId);
                    badge.setVisible(false);
                }
            }
        });

    }
}
