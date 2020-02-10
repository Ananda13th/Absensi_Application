package example.com.absensiapp.view.activity.member;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import example.com.absensiapp.R;
import example.com.absensiapp.view.fragment.member.CheckInFragment;
import example.com.absensiapp.view.fragment.member.SettingFragment;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MemberDashboardActivity extends AppCompatActivity{
    private Fragment fragment;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_dashboard);
        fragment = new CheckInFragment();
        fragmentManager.beginTransaction().replace(R.id.fragmentLayout, fragment).commit();
        setBottomNavgiation();
    }


    private void setBottomNavgiation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigationCheckIn :
                        fragment = new CheckInFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragmentLayout, fragment).commit();
                        break;
                    case R.id.navigationSetting:
                        fragment = new SettingFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragmentLayout, fragment).commit();
                        break;
                }
            }
        });
    }
}
