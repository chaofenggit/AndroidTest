package com.altopay.cropimage.cropone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;


import com.altopay.lib.utils.LogUtil;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {

    private final static String TAG = "ImageUtil";

    /**
     * 处理 图片
     *
     * @param toSize  如果获取到的图片大于toSize,则缩小。
     */
    public static Bitmap resizerBitmap(byte[]data, int toSize) {

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(data,0,data.length,options);
            options.inSampleSize = calculateSampleSize(options.outWidth, options.outHeight, toSize);
            options.inJustDecodeBounds = false;
            return  BitmapFactory.decodeByteArray(data,0,data.length,options);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        return null;
    }
    /**
     * 处理 图片
     *
     * @param context
     * @param uri
     * @param toSize  如果获取到的图片大于toSize,则缩小。
     */
    public static Bitmap resizerBitmap(Context context, Uri uri, int toSize) {

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
            options.inSampleSize = calculateSampleSize(options.outWidth, options.outHeight, toSize);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        return null;
    }


    /**
     * 计算SampleSize
     *
     * @param oriWidth
     * @param oriHeight
     * @param toSize
     * @return
     */
    private static int calculateSampleSize(int oriWidth, int oriHeight, int toSize) {
        int inSampleSize = 1;
        int toWidth = oriWidth;
        int toHeight = oriHeight;
        while (toWidth * toHeight > toSize) {
            inSampleSize *= 2;
            toWidth = oriWidth / inSampleSize;
            toHeight = oriHeight / inSampleSize;
        }
        return inSampleSize;
    }

    /**
     * 读取照片旋转角度
     *
     * @param path 照片路径
     * @return 角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param angle  被旋转角度
     * @param bitmap 图片对象
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmap(int angle, Bitmap bitmap) {
        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵  
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片  
            returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bitmap;
        }
        if (bitmap != returnBm) {
            bitmap.recycle();
        }
        return returnBm;
    }

    /**
     * 等比例缩放
     * @param bm
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap scaleBitmap(Bitmap bm, int newWidth , int newHeight){
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
//        // 计算缩放比例
//        if (width <= newWidth &&  height <= newHeight){
//            return bm;
//        }
//        float scale = Math.max(((float) newWidth) / width, ((float) newHeight) / height);
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(0.5f, 0.5f);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        bm.recycle();
        return newbm;
    }

    /**
     * bitmap 转成 String
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = "";
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = android.util.Base64.encodeToString(bitmapBytes, android.util.Base64.URL_SAFE | android.util.Base64.NO_WRAP);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static String saveFile(Context context, byte[] data) {
        if (context == null){
            LogUtil.d(TAG, "保存图片时，context == null");
            return "";
        }
        if (data == null || data.length == 0){
            LogUtil.d(TAG, "图片数据为空");
            return "";
        }
        File myCaptureFile = new File( "sdcard/123456.jpg");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
      /* 采用压缩转档方法 */
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.write(data, 0, data.length);
           /* 调用flush()方法，更新BufferStream */
            bos.flush();

           /* 结束OutputStream */
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
        return myCaptureFile.getAbsolutePath();
    }

    public static String saveBitmap(Bitmap oriBitmap){
        String fileName = "";

        File myCaptureFile = new File("sdcard/idCardPhoto.jpg");
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            /* 采用压缩转档方法 */
            oriBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
           /* 调用flush()方法，更新BufferStream */
            bos.flush();
            bos.close();
            fileName = myCaptureFile.getAbsolutePath();
        } catch (Exception e){
            e.printStackTrace();
        }
        return fileName;
    }

    public static Point getScreenMetric(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        point.x = display.getWidth();
        point.y = display.getHeight();
        return point;
    }

    /**
     * 裁剪出特定区域的图片
     * @param context
     * @param data  图片原始数据
     * @param cropRect 裁剪区域
     * @return
     */
    public static Bitmap cropBitmap(Context context, byte[] data, int stateBarHeight, Rect cropRect) {
        Bitmap idBitmap = null;
        if (context == null){
            LogUtil.d(TAG, "裁剪图片时，context == null");
            return idBitmap;
        }
        if (data == null || data.length == 0){
            LogUtil.d(TAG, "图片数据为空");
            return idBitmap;
        }
        try {
            Bitmap oriBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Point point = getScreenMetric(context);
            int pic_width = oriBitmap.getWidth();//手机高度 1280
            int  pic_height= oriBitmap.getHeight();//手机宽度720
            float widthScale = pic_width*1.0f/(point.y - stateBarHeight);
            float heightScale = pic_height*1.0f/point.x;
            float x_center,y_center;
            x_center=pic_width*1.0f/2;
            y_center=pic_height*1.0f/2;
            float x = cropRect.top * widthScale;
            float y = (point.x - cropRect.right)*heightScale;
            float width = cropRect.height()*widthScale;
            float height = cropRect.width()*heightScale;
            LogUtil.d(TAG, "cropRect width =  " + cropRect.height() +"height = " + cropRect.width() );
            Matrix matrix = new Matrix();
            matrix.postRotate(360,x_center,y_center);
            idBitmap = Bitmap.createBitmap(oriBitmap, (int)x, (int)y,(int)width, (int)height,matrix,false);
            oriBitmap.recycle();
            idBitmap = rotateBitmap(90,idBitmap);
            LogUtil.d(TAG, "旋转之后图片width =  " + idBitmap.getWidth() +"height = " + idBitmap.getHeight() );
            if (idBitmap.getWidth() > 4096){
                idBitmap = scaleBitmap(idBitmap,4096, 4096);
            }
            LogUtil.d(TAG, "scaleBitmap之后图片width =  " + idBitmap.getWidth() +"height = " + idBitmap.getHeight() );
            idBitmap = compressBitmap(idBitmap, 200*1024);
            LogUtil.d(TAG, "compressBitmap之后图片width =  " + idBitmap.getWidth() +"height = " + idBitmap.getHeight() );
        }catch (Exception e){
            e.printStackTrace();
            return idBitmap;
        }
        return idBitmap;
    }
    /**
     * 压缩图片质量   尺寸大于256*256 小于 4096*4096 小于2M。为了减轻服务端的压力尽量控制在200Kb左右
     * @param image
     * @param maxSize 最大200kb  200*1024
     * @return
     */
    public static Bitmap compressBitmap(Bitmap image, int maxSize) {
        LogUtil.d(TAG, "压缩前：width = " + image.getWidth() + ",height = " + image.getHeight());

        int compressScale = 1;
        if (image.getByteCount() > maxSize){
            compressScale = (int)Math.sqrt(image.getByteCount() /maxSize);
        }
        if (compressScale <= 1){
            compressScale = 1;
        }else {
            while (image.getHeight() < compressScale * 256){
                compressScale --;
                if (compressScale <= 1){
                    compressScale = 1;
                    break;
                }
            }
        }
        LogUtil.d(TAG, "compressScale = " + compressScale);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inSampleSize = compressScale;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        try {
            os.close();
            image.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.d(TAG, "压缩后：width = " + bitmap.getWidth() + ",height = " + bitmap.getHeight());
        return bitmap;
    }
}

