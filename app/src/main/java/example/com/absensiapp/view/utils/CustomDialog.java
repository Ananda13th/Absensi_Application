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
    public Dialog dialog;
    public Button yes, no;
    public SharedPreferences sharedPreferences;

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
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_yes:
                sharedPreferences.edit().clear().apply();
                activity.finish();
                dismiss();
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
