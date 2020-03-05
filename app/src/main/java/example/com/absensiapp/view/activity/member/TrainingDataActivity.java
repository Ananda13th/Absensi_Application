package example.com.absensiapp.view.activity.member;

import androidx.core.app.ActivityCompat;
import ch.zhaw.facerecognitionlibrary.Helpers.CustomCameraView;
import ch.zhaw.facerecognitionlibrary.Helpers.FileHelper;
import ch.zhaw.facerecognitionlibrary.Helpers.MatName;
import ch.zhaw.facerecognitionlibrary.Helpers.MatOperation;
import ch.zhaw.facerecognitionlibrary.PreProcessor.PreProcessorFactory;
import example.com.absensiapp.R;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.SurfaceView;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class TrainingDataActivity extends Activity implements CameraBridgeViewBase.CvCameraViewListener2 {
    public static final int TIME = 0;
    public static final int MANUALLY = 1;
    private CustomCameraView mAddPersonView;
    private long timerDiff;
    private long lastTime;
    private PreProcessorFactory ppF;
    private FileHelper fh;
    private String name;
    private int total;
    private int numberOfPictures;
    private int method;
    private boolean capturePressed;
    private boolean front_camera;
    private boolean night_portrait;
    private int exposure_compensation;

    static {
        if (!OpenCVLoader.initDebug()) {
            Log.d("OpenCV", "OpenCV Not Loaded");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        setContentView(R.layout.activity_training_data);
        SharedPreferences sharedPreferences = this.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        Intent intent = getIntent();
        name = sharedPreferences.getString("Name", "");
        File file = new File(fh.TRAINING_PATH + name);
        if(file.isDirectory() && file.exists()) {
            Intent skipActivity = new Intent(this.getApplicationContext(), MemberDashboardActivity.class);
            startActivity(skipActivity);
            finish();
        }
        method = intent.getIntExtra("Method", 0);
        capturePressed = false;

        fh = new FileHelper();
        total = 0;
        lastTime = new Date().getTime();

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        timerDiff = Integer.valueOf(sharedPrefs.getString("key_timerDiff", "500"));

        mAddPersonView = findViewById(R.id.AddPersonPreview);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        front_camera = sharedPref.getBoolean("key_front_camera", true);

        numberOfPictures = Integer.valueOf(sharedPref.getString("key_numberOfPictures", "100"));

        night_portrait = sharedPref.getBoolean("key_night_portrait", false);
        exposure_compensation = Integer.valueOf(sharedPref.getString("key_exposure_compensation", "50"));

        if (front_camera){
            mAddPersonView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_FRONT);
        } else {
            mAddPersonView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_BACK);
        }
        mAddPersonView.setVisibility(SurfaceView.VISIBLE);
        mAddPersonView.setCvCameraViewListener(this);

        int maxCameraViewWidth = Integer.parseInt(sharedPref.getString("key_maximum_camera_view_width", "640"));
        int maxCameraViewHeight = Integer.parseInt(sharedPref.getString("key_maximum_camera_view_height", "480"));
        mAddPersonView.setMaxFrameSize(maxCameraViewWidth, maxCameraViewHeight);
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

        if (night_portrait) {
            mAddPersonView.setNightPortrait();
        }

        if (exposure_compensation != 50 && 0 <= exposure_compensation && exposure_compensation <= 100)
            mAddPersonView.setExposure(exposure_compensation);
    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat imgRgba = inputFrame.rgba();
        Mat imgCopy = new Mat();
        imgRgba.copyTo(imgCopy);
        if(front_camera){
            Core.flip(imgRgba,imgRgba,1);
        }

        long time = new Date().getTime();
        if((method == MANUALLY) || (method == TIME) && (lastTime + timerDiff < time)){
            lastTime = time;

            List<Mat> images = ppF.getCroppedImage(imgCopy);
            if (images != null && images.size() == 1){
                Mat img = images.get(0);
                if(img != null){
                    Rect[] faces = ppF.getFacesForRecognition();
                    if((faces != null) && (faces.length == 1)){
                        faces = MatOperation.rotateFaces(imgRgba, faces, ppF.getAngleForRecognition());
                        if(((method == MANUALLY) && capturePressed) || (method == TIME)){
                            MatName m = new MatName(name + "_" + total, img);
                            String wholeFolderPath = fh.TRAINING_PATH + name;
                            new File(wholeFolderPath).mkdirs();
                            fh.saveMatToImage(m, wholeFolderPath + "/");

                            //Save Dummy Data 2
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.unknown);
                            String dummyFolderPath = fh.TRAINING_PATH + "???";
                            File file = new File(dummyFolderPath, "???"+"_"+total+".png");
                            new File(dummyFolderPath).mkdirs();
                            try {
                                FileOutputStream out = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                out.flush();
                                out.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.getMessage();
                            }

                            for(int i = 0; i<faces.length; i++){
                                MatOperation.drawRectangleAndLabelOnPreview(imgRgba, faces[i], String.valueOf(total), front_camera);
                            }

                            total++;

                            if(total >= numberOfPictures){
                                Intent intent = new Intent(getApplicationContext(),MemberDashboardActivity.class);
                                startActivity(intent);
                            }
                            capturePressed = false;
                        } else {
                            for(int i = 0; i<faces.length; i++){
                                MatOperation.drawRectangleOnPreview(imgRgba, faces[i], front_camera);
                            }
                        }
                    }
                }
            }
        }

        return imgRgba;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        ppF = new PreProcessorFactory(this);
        mAddPersonView.enableView();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mAddPersonView != null)
            mAddPersonView.disableView();
    }

    public void onDestroy() {
        super.onDestroy();
        if (mAddPersonView != null)
            mAddPersonView.disableView();
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void checkPermission() {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.INTERNET
        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

}
