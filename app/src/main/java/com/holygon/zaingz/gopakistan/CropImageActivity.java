package com.holygon.zaingz.gopakistan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CropImageActivity extends AppCompatActivity {


    private static int filter_code = 0;


    private ImageView mCropImageView,
            e1,e2, e3,e4,e5,e6,e7, e8,e9,e10,e11,e12,e13,e14,e15, e16,e17;
    ImageView[] Eimages = new ImageView[16];
    Uri myUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);

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

        Bundle extras = getIntent().getExtras();
        myUri = Uri.parse(extras.getString("imageUri"));


        mCropImageView = (ImageView) findViewById(R.id.CropImageView);


      /*  e1 = (ImageView) findViewById(R.id.e1);
        e2 = (ImageView) findViewById(R.id.e2);
        e3 = (ImageView) findViewById(R.id.e3);
        e4 = (ImageView) findViewById(R.id.e4);
*/
        mCropImageView.setImageURI(myUri);
        e1 = (ImageView) findViewById(R.id.e1);
        e1.setImageURI(myUri);

        e2 = (ImageView) findViewById(R.id.e2);
        Eimages[0] = e2;
        e3 = (ImageView) findViewById(R.id.e3);
        Eimages[1] = e3;
        e4 = (ImageView) findViewById(R.id.e4);
        Eimages[2] = e4;
        e5 = (ImageView) findViewById(R.id.e5);
        Eimages[3] = e5;
        e6 = (ImageView) findViewById(R.id.e6);
        Eimages[4] = e6;
        e7 = (ImageView) findViewById(R.id.e7);
        Eimages[5] = e7;
        e8 = (ImageView) findViewById(R.id.e8);
        Eimages[6] = e8;
        e9 = (ImageView) findViewById(R.id.e9);
        Eimages[7] = e9;
        e10 = (ImageView) findViewById(R.id.e10);
        Eimages[8] = e10;
        e11 = (ImageView) findViewById(R.id.e11);
        Eimages[9] = e11;
        e12 = (ImageView) findViewById(R.id.e12);
        Eimages[10] = e12;
        e13 = (ImageView) findViewById(R.id.e13);
        Eimages[11] = e13;
        e14 = (ImageView) findViewById(R.id.e14);
        Eimages[12] = e14;
        e15 = (ImageView) findViewById(R.id.e15);
        Eimages[13] = e15;
        e16 = (ImageView) findViewById(R.id.e16);
        Eimages[14] = e16;
        e17 = (ImageView) findViewById(R.id.e17);
        Eimages[15] = e17;





        for (int i=0; i<16; i++){
            new EffectTask(this,Eimages[i],myUri, i+1).execute();
        }







        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropImageView.setImageURI(myUri);

            }
        });



        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EffectTask(CropImageActivity.this,mCropImageView,myUri, 1).execute();
                filter_code = 1;
            }
        });


        e3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EffectTask(CropImageActivity.this,mCropImageView,myUri, 2).execute();
                filter_code = 2;
            }
        });



        e4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EffectTask(CropImageActivity.this,mCropImageView,myUri, 3).execute();
                filter_code = 3;
            }
        });


        e5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EffectTask(CropImageActivity.this,mCropImageView,myUri, 4).execute();
                filter_code = 4;
            }
        });


        e6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EffectTask(CropImageActivity.this,mCropImageView,myUri, 5).execute();
                filter_code = 5;
            }
        });


        e7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EffectTask(CropImageActivity.this,mCropImageView,myUri, 6).execute();
                filter_code = 6;
            }
        });


        e8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EffectTask(CropImageActivity.this,mCropImageView,myUri, 7).execute();
                filter_code = 7;
            }
        });


        e9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EffectTask(CropImageActivity.this,mCropImageView,myUri, 8).execute();
                filter_code = 8;
            }
        });


        e10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EffectTask(CropImageActivity.this,mCropImageView,myUri, 9).execute();
                filter_code = 9;
            }
        });


        e11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EffectTask(CropImageActivity.this,mCropImageView,myUri, 10).execute();
                filter_code = 10;
            }
        });


        e12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EffectTask(CropImageActivity.this,mCropImageView,myUri, 11).execute();
                filter_code = 11;
            }
        });


        e13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EffectTask(CropImageActivity.this,mCropImageView,myUri, 12).execute();
                filter_code = 12;
            }
        });


        e14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EffectTask(CropImageActivity.this,mCropImageView,myUri, 13).execute();
                filter_code = 13;
            }
        });


        e15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EffectTask(CropImageActivity.this,mCropImageView,myUri, 14).execute();
                filter_code = 14;
            }
        });


        e16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EffectTask(CropImageActivity.this,mCropImageView,myUri, 15).execute();
                filter_code = 15;
            }
        });

        e17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EffectTask(CropImageActivity.this,mCropImageView,myUri, 16).execute();
                filter_code = 16;
            }
        });











    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.next:
                Log.d("y", "a");
                Intent mainIntent = new Intent(CropImageActivity.this, ResultActivity.class);
                //mainIntent.putExtra("imageUri", resultUri.toString());

                mainIntent.putExtra("filter_code", filter_code);
                mainIntent.putExtra("imageUri", myUri.toString());



                CropImageActivity.this.startActivity(mainIntent);

            default:
                return super.onOptionsItemSelected(item);
        }
    }



}//end of activity
