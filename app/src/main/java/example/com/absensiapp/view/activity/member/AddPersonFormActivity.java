package example.com.absensiapp.view.activity.member;

import androidx.appcompat.app.AppCompatActivity;
import ch.zhaw.facerecognitionlibrary.Helpers.FileHelper;
import example.com.absensiapp.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;

public class AddPersonFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person_form);
        final ToggleButton btnTrainingTest = findViewById(R.id.btnTrainingTest);
        final ToggleButton btnReferenceDeviation = findViewById(R.id.btnReferenceDeviation);
        final ToggleButton btnTimeManually = findViewById(R.id.btnTimeManually);
        btnTrainingTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnTrainingTest.isChecked()){
                    btnReferenceDeviation.setEnabled(true);
                } else {
                    btnReferenceDeviation.setEnabled(false);
                }
            }
        });

        Button btn_Start = findViewById(R.id.btn_Start);
        btn_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txt_Name = findViewById(R.id.txt_Name);
                String name = txt_Name.getText().toString();
                Intent intent = new Intent(v.getContext(), TrainingDataActivity.class);
                intent.putExtra("Name", name);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                if(btnTimeManually.isChecked()){
                    intent.putExtra("Method", TrainingDataActivity.MANUALLY);
                } else {
                    intent.putExtra("Method", TrainingDataActivity.TIME);
                }

                if(btnTrainingTest.isChecked()){
                    if(isNameAlreadyUsed(new FileHelper().getTestList(), name)){
                        Toast.makeText(getApplicationContext(), "This name is already used. Please choose another one.", Toast.LENGTH_SHORT).show();
                    } else {
                        intent.putExtra("Folder", "Test");
                        if(btnReferenceDeviation.isChecked()){
                            intent.putExtra("Subfolder", "deviation");
                        } else {
                            intent.putExtra("Subfolder", "reference");
                        }
                        startActivity(intent);
                    }
                } else {

                    if(isNameAlreadyUsed(new FileHelper().getTrainingList(), name)){
                        Toast.makeText(getApplicationContext(), "This name is already used. Please choose another one.", Toast.LENGTH_SHORT).show();
                    } else {
                        intent.putExtra("Folder", "Training");
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private boolean isNameAlreadyUsed(File[] list, String name){
        boolean used = false;
        if(list != null && list.length > 0){
            for(File person : list){
                String[] tokens = person.getAbsolutePath().split("/");
                final String foldername = tokens[tokens.length-1];
                if(foldername.equals(name)){
                    used = true;
                    break;
                }
            }
        }
        return used;
    }
}
