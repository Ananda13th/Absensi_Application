package example.com.absensiapp.view.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import example.com.absensiapp.R;
import example.com.absensiapp.view.activity.LoginActivity;

public class CustomDialog extends Dialog implements View.OnClickListener {

    public Activity activity;
    private SharedPreferences sharedPreferences;

    public CustomDialog(Activity activity) {
        super(activity);

        this.activity=activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        sharedPreferences = activity.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        setContentView(R.layout.confirmation_dialog_layout);
        Button yes = findViewById(R.id.btn_yes);
        Button no = findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_yes:
                sharedPreferences.edit().clear().apply();
                dismiss();
                Intent intent = new Intent(activity, LoginActivity.class);
                activity.startActivity(intent);
                activity.finish();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

}
