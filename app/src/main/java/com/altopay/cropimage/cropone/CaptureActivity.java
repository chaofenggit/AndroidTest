package com.altopay.cropimage.cropone;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.altopay.cropimage.R;
import com.altopay.lib.utils.ToastUtil;
import com.altopay.lib.utils.logutil.LogUtil;

import java.lang.reflect.Field;

/**
 * @author admin
 */
public class CaptureActivity extends Activity implements View.OnClickListener {

    private static final String TAG = CaptureActivity.class.getSimpleName();

    private RelativeLayout scanCropView;
    /**
     * 拍照按钮
     */
    private ImageView altopay_camera_take_pic;
    /**
     * 照片预览区域
     */
    private CameraSurfaceView cameraSurfaceView;

    private IDViewFinder idViewFinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.altopay_camera_layout_one);
        initViews();
    }

    private void initViews() {
        scanCropView = (RelativeLayout) findViewById(R.id.capture_crop_view);
        altopay_camera_take_pic = (ImageView) findViewById(R.id.altopay_camera_take_pic);
        altopay_camera_take_pic.setOnClickListener(this);
        idViewFinder = findViewById(R.id.idViewFinder);
        cameraSurfaceView = (CameraSurfaceView) findViewById(R.id.altopay_camera_view);
        cameraSurfaceView.setOnClickListener(this);
        cameraSurfaceView.setListener(new CameraSurfaceView.TakePic(){

            @Override
            public void onTakePic(byte[] data) {
                if (data != null && data.length > 0){
                    String filePath = ImageUtil.saveBitmap(ImageUtil.cropBitmap(CaptureActivity.this, data, getStatusBarHeight(),initCrop()));
                    if (!TextUtils.isEmpty(filePath)){
                        Intent intent = new Intent(CaptureActivity.this, ShowImageActivity.class);
                        intent.putExtra("filePath", filePath);
                        startActivity(intent);
                    }else{
                        ToastUtil.showToastLong(getApplicationContext(),"保存失败");
                        cameraSurfaceView.onResume();
                    }

                }else{
                    LogUtil.d(TAG,"相册数据为空");
                }

            }
        } );
        idViewFinder.post(new Runnable() {
            @Override
            public void run() {
                    idViewFinder.setRect(initCrop());
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.altopay_camera_take_pic) {
            takePic();
        }
    }

    /**
     * 拍照
     */
    public void takePic(){
        if (checkCameraHardware()){
            cameraSurfaceView.takePicture();
        }else{
            LogUtil.d(TAG,"没有找到可用的摄像头");
        }
    }

    /**
     * 检查是否有可用的摄像头
     * @return
     */
    private boolean checkCameraHardware() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return true;
        } else {
            return false;
        }
    }

    //region  初始化截取的矩形区域
    public Rect initCrop() {
        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft - 10;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop -10;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth + 20;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight + 20;
        /** 生成最终的截取的矩形 */
        return new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSurfaceView.onPause();
    }
}
