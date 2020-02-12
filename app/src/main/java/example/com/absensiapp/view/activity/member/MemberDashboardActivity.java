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
import example.com.absensiapp.view.fragment.member.SettingFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.List;

public class MemberDashboardActivity extends AppCompatActivity{
    private Fragment fragment;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_dashboard);
        //new initTrainingModel().execute();
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

//    private class initTrainingModel extends AsyncTask<Void, Void, Void>{
//        ProgressDialog dialog = new ProgressDialog(MemberDashboardActivity.this);
//
//        @Override
//        protected void onPreExecute() {
//            dialog.setMessage("Initializing...");
//            dialog.setCancelable(false);
//            dialog.show();
//            super.onPreExecute();
//        }
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            final Handler handler = new Handler(Looper.getMainLooper());
//            thread = new Thread(new Runnable() {
//                public void run() {
//                    if(!Thread.currentThread().isInterrupted()){
//                        PreProcessorFactory ppF = new PreProcessorFactory(getApplicationContext());
//                        PreferencesHelper preferencesHelper = new PreferencesHelper(getApplicationContext());
//                        String algorithm = preferencesHelper.getClassificationMethod();
//
//                        FileHelper fileHelper = new FileHelper();
//                        fileHelper.createDataFolderIfNotExsiting();
//                        final File[] persons = fileHelper.getTrainingList();
//                        if (persons.length > 0) {
//                            Recognition rec = RecognitionFactory.getRecognitionAlgorithm(getApplicationContext(), Recognition.TRAINING, algorithm);
//                            for (File person : persons) {
//                                if (person.isDirectory()){
//                                    File[] files = person.listFiles();
//                                    int counter = 1;
//                                    for (File file : files) {
//                                        if (FileHelper.isFileAnImage(file)){
//                                            Mat imgRgb = Imgcodecs.imread(file.getAbsolutePath());
//                                            Imgproc.cvtColor(imgRgb, imgRgb, Imgproc.COLOR_BGRA2RGBA);
//                                            Mat processedImage = new Mat();
//                                            imgRgb.copyTo(processedImage);
//                                            List<Mat> images = ppF.getProcessedImage(processedImage, PreProcessorFactory.PreprocessingMode.RECOGNITION);
//                                            if (images == null || images.size() > 1) {
//                                                // More than 1 face detected --> cannot use this file for training
//                                                continue;
//                                            } else {
//                                                processedImage = images.get(0);
//                                            }
//                                            if (processedImage.empty()) {
//                                                continue;
//                                            }
//                                            // The last token is the name --> Folder name = Person name
//                                            String[] tokens = file.getParent().split("/");
//                                            final String name = tokens[tokens.length - 1];
//
//                                            MatName m = new MatName("processedImage", processedImage);
//                                            fileHelper.saveMatToImage(m, FileHelper.DATA_PATH);
//
//                                            rec.addImage(processedImage, name, false);
//
////                                          fileHelper.saveCroppedImage(imgRgb, ppF, file, name, counter);
//
//                                            // Update screen to show the progress
//                                            final int counterPost = counter;
//                                            final int filesLength = files.length;
//                                            Log.d("LOG TRAINING", "Image " + counterPost + " of " + filesLength + " from " + name + " imported.\n");
////                                            progress.post(new Runnable() {
////                                                @Override
////                                                public void run() {
////                                                    progress.append("Image " + counterPost + " of " + filesLength + " from " + name + " imported.\n");
////                                                }
////                                            });
//                                            counter++;
//                                        }
//                                    }
//                                }
//                            }
////                            final Intent intent = new Intent(getApplicationContext(),MemberDashboardActivity.class);
////                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                            if (rec.train()) {
////                                intent.putExtra("training", "Training successful");
////                            } else {
////                                intent.putExtra("training", "Training failed");
////                            }
////                            handler.post(new Runnable() {
////                                @Override
////                                public void run() {
////                                    startActivity(intent);
////                                }
////                            });
//                        } else {
//                            Thread.currentThread().interrupt();
//                        }
//                    }
//                }
//            });
//            thread.start();
//            return null;
//        }
//
//        protected void onPostExecute(Void result) {
//            if(dialog != null && dialog.isShowing()){
//                dialog.dismiss();
//            }
//        }
//    }
}
