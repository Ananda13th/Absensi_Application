package example.com.absensiapp.view.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import example.com.absensiapp.model.UserModel;

public class PrefManager {

    Context context;

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
        editor.putString("Data", "null");
        editor.putString("HasTrainData", "false");
        editor.commit();
    }

    public boolean isUserLogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean isUserIdEmpty = sharedPreferences.getString("UserId", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("Password", "").isEmpty();
        return isUserIdEmpty;
    }

    public boolean isAdmin() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean isAdmin = sharedPreferences.getString("Role", "").matches("admin");
        return isAdmin;
    }

    public boolean isDataExist() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean data = sharedPreferences.getString("Data", "").matches("true");
        return data;
    }
}
