package example.com.absensiapp.view.activity.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import example.com.absensiapp.R;
import example.com.absensiapp.view.fragment.admin.OverrideFragment;
import example.com.absensiapp.view.fragment.admin.UserListFragment;
import example.com.absensiapp.view.fragment.member.HistoryFragment;
import example.com.absensiapp.view.fragment.member.SettingFragment;
import example.com.absensiapp.view.utils.CustomDialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdminBoardActivity extends AppCompatActivity {

    private Fragment fragment;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        setBottomNavgiation();
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

    private void setBottomNavgiation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
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
                        break;
                }
            }
        });
    }
}
