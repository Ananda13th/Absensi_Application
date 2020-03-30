package example.com.absensiapp.view.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import example.com.absensiapp.model.UserModel;

public class PrefManager {

    private Context context;

    public PrefManager(Context context) {
        this.context = context;
    }

    public void saveLoginDetails(UserModel userModel) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserId", userModel.getUserId());
        editor.putString("Password", userModel.getPassword());
        editor.putString("Name", userModel.getName());
        editor.putString("Role", userModel.getRole());
        editor.putBoolean("SynchData", false);
        editor.apply();
    }

    public boolean isUserLogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("UserId", "").isEmpty();
    }

    public boolean isAdmin() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Role", "").matches("admin");
    }
}
