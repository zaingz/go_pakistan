package com.holygon.zaingz.gopakistan;

import android.*;
import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

public class ResultActivity extends AppCompatActivity {


    ImageView mCropImageView;
    Uri myUri;
    int filter_code = 0;
    Bitmap bitmap = null;
    Bitmap flag = null, moon = null, stamp = null;
    InterstitialAd mInterstitialAd;
    private static String TAG = "PermissionDemo";
    Bitmap orignal;


    private static final int REQUEST_WRITE_STORAGE = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1493638564405387~6483954550");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.gotext);
        Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 680, 100, true);

        getSupportActionBar().setLogo(new BitmapDrawable(getResources(), bMapScaled));

        getSupportActionBar().setDisplayUseLogoEnabled(true);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1493638564405387/9437420956");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();

            }
        });

        requestNewInterstitial();


        Bundle extras = getIntent().getExtras();
        myUri = Uri.parse(extras.getString("imageUri"));
        filter_code = extras.getInt("filter_code", 0);

        mCropImageView = (ImageView) findViewById(R.id.CropImageView);


        for (int i = 0; i < 16; i++) {
            if ((filter_code - 1) == i) {
                Log.i("zaingz", "in if");
                new EffectTask(this, mCropImageView, myUri, filter_code).execute();

                break;
            }
        }


    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "On Start .....");

    }

    public void saveImage(View view) {

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        //ask for the permission in android M
        int permission = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if (checkPermission()) {

            orignal = ((BitmapDrawable) mCropImageView.getDrawable()).getBitmap();
            savePhoto(orignal);


        } else {

            if (permission != PackageManager.PERMISSION_GRANTED) {
                Log.d("per", "in if");
                Log.i(TAG, "Permission to record denied");

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Permission to access the SD-CARD is required")
                            .setTitle("Permission required");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {
                            Log.i(TAG, "Clicked");
                            makeRequest();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    Log.d("per", "in else");
                    makeRequest();


                }
            }
        }
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_WRITE_STORAGE);
    }


    public void savePhoto(Bitmap bmp) {
        File imageFileFolder = new File(Environment.getExternalStorageDirectory(), "Go Pakistan");
        imageFileFolder.mkdir();
        FileOutputStream out = null;
        Calendar c = Calendar.getInstance();
        String date = fromInt(c.get(Calendar.MONTH))
                + fromInt(c.get(Calendar.DAY_OF_MONTH))
                + fromInt(c.get(Calendar.YEAR))
                + fromInt(c.get(Calendar.HOUR_OF_DAY))
                + fromInt(c.get(Calendar.MINUTE))
                + fromInt(c.get(Calendar.SECOND));
        File imageFileName = new File(imageFileFolder, date.toString() + ".jpg");
        try {
            out = new FileOutputStream(imageFileName);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            Toast.makeText(ResultActivity.this, "Images Saved", Toast.LENGTH_LONG).show();
            scanPhoto(imageFileName.toString());
            out = null;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String fromInt(int val) {
        return String.valueOf(val);
    }

    MediaScannerConnection msConn;

    public void scanPhoto(final String imageFileName) {
        msConn = new MediaScannerConnection(ResultActivity.this, new MediaScannerConnection.MediaScannerConnectionClient() {
            public void onMediaScannerConnected() {
                msConn.scanFile(imageFileName, null);

            }

            public void onScanCompleted(String path, Uri uri) {
                msConn.disconnect();

            }
        });
        msConn.connect();
    }


    public void shareImage(View view) {
//
//        Bitmap bmp11 = null;
//
//       bmp11 = orignal;
//        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bmp11,"Go Pakistan", "Created using lopop");
//        Uri bitmapUri = Uri.parse(bitmapPath);
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("image/png");
//
//        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri );
//
//        startActivity(Intent.createChooser(intent , "Share"));


        View content = findViewById(R.id.CropImageView);
        content.setDrawingCacheEnabled(true);

        Bitmap bitmap = content.getDrawingCache();
        File root = Environment.getExternalStorageDirectory();
        File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/image.jpg");
        try {
            cachePath.createNewFile();
            FileOutputStream ostream = new FileOutputStream(cachePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(cachePath));
        startActivity(Intent.createChooser(share, "GO Pakistan"));










}
    public void about(View view) {

        Uri uri = Uri.parse("market://details?id=" + ResultActivity.this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            ResultActivity.this.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {

        }
       
    }






    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG, "Permission has been denied by user");

                } else {


                   savePhoto(bitmap);

                }
                return;
            }
        }
    }




}
