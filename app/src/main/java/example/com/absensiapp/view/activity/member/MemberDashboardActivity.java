package example.com.absensiapp.view.activity.member;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import ch.zhaw.facerecognitionlibrary.Helpers.FileHelper;
import ch.zhaw.facerecognitionlibrary.Helpers.MatName;
import ch.zhaw.facerecognitionlibrary.Helpers.PreferencesHelper;
import ch.zhaw.facerecognitionlibrary.PreProcessor.PreProcessorFactory;
import ch.zhaw.facerecognitionlibrary.Recognition.Recognition;
import ch.zhaw.facerecognitionlibrary.Recognition.RecognitionFactory;
import example.com.absensiapp.R;
import example.com.absensiapp.view.fragment.member.CheckInFragment;
import example.com.absensiapp.view.fragment.member.HistoryFragment;
import example.com.absensiapp.view.fragment.member.SettingFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.List;

public class MemberDashboardActivity extends AppCompatActivity{
    private Fragment fragment;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    static {
        if (!OpenCVLoader.initDebug()) {
            Log.d("OpenCV", "OpenCV Not Loadede!");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_dashboard);

        SharedPreferences sharedPreferences = this.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String hasTrainData = sharedPreferences.getString("HasTrainData", "");
//        if(hasTrainData.equals("false")) {
//            new initTrainingModel().execute();
//            sharedPreferences.edit().putString("HasTrainData", "true").apply();
//        }
        fragment = new CheckInFragment();
        fragmentManager.beginTransaction().replace(R.id.fragment_layout, fragment).commit();
        setBottomNavgiation();
    }


    private void setBottomNavgiation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_check_in:
                        fragment = new CheckInFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragment_layout, fragment).commit();
                        break;
                    case R.id.navigation_setting:
                        fragment = new SettingFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragment_layout, fragment).commit();
                        break;
                    case R.id.navigation_history:
                        fragment = new HistoryFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragment_layout, fragment).commit();
                        break;
                }
                return false;
            }

        });
    }

    private class initTrainingModel extends AsyncTask<Void, Void, Void>{
        ProgressDialog dialog = new ProgressDialog(MemberDashboardActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Initializing...");
            dialog.setCancelable(false);
            dialog.show();
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            PreProcessorFactory ppF = new PreProcessorFactory(getApplicationContext());
            PreferencesHelper preferencesHelper = new PreferencesHelper(getApplicationContext());
            String algorithm = preferencesHelper.getClassificationMethod();

            FileHelper fileHelper = new FileHelper();
            fileHelper.createDataFolderIfNotExsiting();
            final File[] persons = fileHelper.getTrainingList();
            if (persons.length > 0) {
                Recognition rec = RecognitionFactory.getRecognitionAlgorithm(getApplicationContext(), Recognition.TRAINING, algorithm);
                for (File person : persons) {
                    if (person.isDirectory()){
                        File[] files = person.listFiles();
                        int counter = 1;
                        for (File file : files) {
                            if (FileHelper.isFileAnImage(file)){
                                Mat imgRgb = Imgcodecs.imread(file.getAbsolutePath());
                                Imgproc.cvtColor(imgRgb, imgRgb, Imgproc.COLOR_BGRA2RGBA);
                                Mat processedImage = new Mat();
                                imgRgb.copyTo(processedImage);
                                List<Mat> images = ppF.getProcessedImage(processedImage, PreProcessorFactory.PreprocessingMode.RECOGNITION);
                                if (images == null || images.size() > 1) {
                                    continue;
                                } else {
                                    processedImage = images.get(0);
                                }
                                if (processedImage.empty()) {
                                    continue;
                                }
                                String[] tokens = file.getParent().split("/");
                                final String name = tokens[tokens.length - 1];

                                MatName m = new MatName("processedImage", processedImage);
                                fileHelper.saveMatToImage(m, FileHelper.DATA_PATH);

                                rec.addImage(processedImage, name, false);

                                counter++;
                            }
                        }
                    }
                }
            } else {
                Thread.currentThread().interrupt();
            }
        return null;
        }

        protected void onPostExecute(Void result) {
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }

}
