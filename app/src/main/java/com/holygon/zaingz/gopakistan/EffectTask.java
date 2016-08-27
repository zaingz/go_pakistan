package com.holygon.zaingz.gopakistan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by zain on 8/12/16.
 */
public class EffectTask extends AsyncTask<Void, Void, Bitmap> {

    Context context;
    ImageView imageView;
    Uri bitmapUri;
    int filter_code, alpha;

    public EffectTask(Context context, ImageView imageView, Uri bitmapUri, int filter_code){
        this.context = context;
        this.imageView = imageView;
        this.bitmapUri = bitmapUri;
        this.filter_code = filter_code;

    }

    @Override
    protected Bitmap doInBackground(Void... params) {

        Bitmap orignal = null;
        Bitmap overlay = null;


        switch (filter_code){
            case 1:
                overlay = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.e1);
                alpha = 640;
                break;
            case 2:
                overlay = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.e2);
                alpha = 620;
                break;
            case 3:
                overlay = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.e3);
                alpha = 940;
                break;
            case 4:
                overlay = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.e4);
                alpha = 1000;
                break;
            case 5:
                overlay = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.e5);
                alpha = 940;
                break;
            case 6:
                overlay = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.e6);
                alpha = 1000;
                break;
            case 7:
                overlay = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.e7);
                alpha = 1000;
                break;
            case 8:
                overlay = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.e8);
                alpha = 1000;
                break;
            case 9:
                overlay = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.e9);
                alpha = 640;
                break;
            case 10:
                overlay = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.e10);
                alpha = 1000;
                break;
            case 11:
                overlay = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.e11);
                alpha = 940;
                break;
            case 12:
                overlay = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.e12);
                alpha = 1000;
                break;
            case 13:
                overlay = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.e13);
                alpha = 1000;
                break;
            case 14:
                overlay = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.e14);
                alpha = 1000;
                break;
            case 15:
                overlay = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.e15);
                alpha = 1000;
                break;
            case 16:
                overlay = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.e16);
                alpha = 640;
                break;


        }



        try {
            orignal = MediaStore.Images.Media.getBitmap(context.getContentResolver(),bitmapUri);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return overlayWithAplha(orignal,overlay,alpha);

    }


    protected void onPostExecute(Bitmap result) {


        imageView.setImageBitmap(result);


    }


    public static Bitmap overlayWithAplha(Bitmap bmp1, Bitmap bmp2,int alpha)
    {
        try
        {
            Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(),  bmp1.getConfig());
            Canvas canvas = new Canvas(bmOverlay);
            canvas.drawBitmap(bmp1, 0, 0, null);

            Paint alphaPaint = new Paint();
            alphaPaint.setAlpha(alpha);
            canvas.drawBitmap(getResizedBitmap(bmp2, bmp1.getWidth(), bmp1.getHeight()), 0, 0, alphaPaint);
            return bmOverlay;

        } catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }


    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }


















}
