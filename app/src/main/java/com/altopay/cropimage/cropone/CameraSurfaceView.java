package com.altopay.cropimage.cropone;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.altopay.lib.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * https://www.jianshu.com/p/7f766eb2f4e7
 * https://github.com/tianyalian/CropperCammer
 */

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    private static final String TAG = "CameraSurfaceView";

    public CameraSurfaceView(Context context) {
        this(context,null);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private SurfaceHolder mHolder;
    private Camera mCamera;
    private int mScreenWidth;
    private int mScreenHeight;
    private TakePic listener;
    public void setListener(TakePic listener) {
        this.listener = listener;
    }

    /**
     * 初始化
     */
    private void initView(Context context) {
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        getScreenMetric(context);
    }

    private void getScreenMetric(Context context) {
        WindowManager WM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        WM.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = WM.getDefaultDisplay().getWidth();
        mScreenHeight = WM.getDefaultDisplay().getHeight();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogUtil.d(TAG,"****************surfaceCreated");
        if (mCamera == null){
            try {
                mCamera = Camera.open();//后置摄像头
                initFromCameraParameters(mCamera, mScreenWidth, mScreenHeight);
                mCamera.setPreviewDisplay(holder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initFromCameraParameters(Camera camera, int width, int height) {
        LogUtil.i(TAG,"setCameraParams  width="+width+"  height="+height);
        Camera.Parameters parameters = mCamera.getParameters();
        // 获取摄像头支持的PictureSize列表
        List<Camera.Size> pictureSizeList = parameters.getSupportedPictureSizes();
//        for (Camera.Size size : pictureSizeList) {
//            LogUtil.i(TAG, "分辨率pictureSizeList size.width=" + size.width + "  size.height=" + size.height);
//        }
        /**从列表中选取合适的分辨率*/
        Camera.Size picSize = getProperPicSize(pictureSizeList,height ,width);
        if (null == picSize) {
//            Log.i(TAG, "null == picSize");
            picSize = parameters.getPictureSize();
        }
        LogUtil.i(TAG, "picSize.width=" + picSize.width + "  picSize.height=" + picSize.height);
        // 根据选出的PictureSize重新设置SurfaceView大小
        float w = picSize.width;
        float h = picSize.height;
        parameters.setPictureSize(picSize.width,picSize.height);
        this.setLayoutParams(new RelativeLayout.LayoutParams((int) (height*(h/w)), height));

        // 获取摄像头支持的PreviewSize列表
        List<Camera.Size> previewSizeList = parameters.getSupportedPreviewSizes();

//        for (Camera.Size size : previewSizeList) {
//            LogUtil.i(TAG, "预览图片SizeList size.width=" + size.width + "  size.height=" + size.height);
//        }
        Camera.Size preSize = getProperPicSize(previewSizeList, height, width);
        if (null != preSize) {
            LogUtil.i(TAG, "preSize.width=" + preSize.width + "  preSize.height=" + preSize.height);
            parameters.setPreviewSize(preSize.width, preSize.height);
        }

        parameters.setJpegQuality(100); // 设置照片质量
        if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);// 连续对焦模式
        }
        mCamera.setDisplayOrientation(90);// 设置PreviewDisplay的方向，效果就是将捕获的画面旋转多少度显示
//        //设置图片格式
        parameters.setPictureFormat(ImageFormat.JPEG);
//        //设置图片的质量
        parameters.set("jpeg-quality", 100);
        mCamera.setParameters(parameters);
    }

    /**
     * 从列表中选取合适的分辨率
     * 默认w:h = 4:3
     * <p>注意：这里的w对应屏幕的height
     *            h对应屏幕的width<p/>
     */
    private Camera.Size getProperPicSize(List<Camera.Size> pictureSizeList, int height, int width) {
        Camera.Size result = null;
        /**
         * 1.先判断有没有跟手机同尺寸的分辨率
         */
        for (Camera.Size size : pictureSizeList) {
            if (height == size.width && width == size.height){
                result = size;
                break;
            }
        }
        /**
         * 2.去除低分辨率的数据
         * 然后判断有没有跟手机屏幕宽高比例相同的尺寸
         */
        if (null == result){
            if (height >= 1280 || width >= 720){
                List<Camera.Size> removeList = new ArrayList<>(pictureSizeList.size());
                for (Camera.Size size : pictureSizeList){
                    if (size.width < 960){
                        removeList.add(size);
                    }
                }
                for (int i = 0; i < removeList.size(); i++) {
                    pictureSizeList.remove(removeList.get(i));
                }
            }
            float screenRatio = 0;
            if (getHeight() >0 && getWidth() >0){
                screenRatio =  ((float)getHeight())/getWidth();
            }else{
                screenRatio = ((float) height) /width;
            }
            Log.i(TAG, "screenRatio=" + screenRatio);
            for (Camera.Size size : pictureSizeList) {
                float currentRatio = (size.width*1.0f) / size.height;
                if (currentRatio - screenRatio == 0) {
                    result = size;
                    break;
                }
            }
        }
        /**
         * 3、判断有没有跟手机宽高比相近的尺寸
         */
        if (null == result) {
            float screenRatio = 0;
            if (getHeight() >0 && getWidth() >0){
                screenRatio =  ((float)getHeight())/getWidth();
            }else{
                screenRatio = ((float) height) /width;
            }
            float change = 1;
            for (int i = 0; i < pictureSizeList.size(); i++) {
                float cameraRatio =  ((float)pictureSizeList.get(i).width)/pictureSizeList.get(i).height;
                if (Math.abs(cameraRatio - screenRatio) < change && cameraRatio <= screenRatio){
                    change = Math.abs(cameraRatio - screenRatio);
                    result = pictureSizeList.get(i);
                }
            }
        }

        if (null == result) {
            for (Camera.Size size : pictureSizeList) {
                float curRatio = ((float) size.width) / size.height;
                if (curRatio == 4f / 3) {// 默认w:h = 4:3
                    result = size;
                    break;
                }
            }
        }
//        测试使用
        for (int i = 0; i < pictureSizeList.size(); i++) {
            LogUtil.i(TAG, "分辨率pictureSizeList size.width=" + pictureSizeList.get(i).width
                    + "  size.height=" + pictureSizeList.get(i).height
                    + ",Ratio = " + pictureSizeList.get(i).width*1.0f/pictureSizeList.get(i).height);
        }
        return result;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
        LogUtil.d(TAG,"****************surfaceChanged");
        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            LogUtil.d(TAG, "Error starting mCamera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LogUtil.d(TAG,"****************surfaceChanged");
        if (mCamera != null){
            mCamera.cancelAutoFocus();
            mCamera.stopPreview();
            mCamera.release();
        }
        mCamera = null;
        holder = null;
    }

    /**
     *对外提供的拍照方法
     */
    public void takePicture() {
        if (mCamera == null){
            return;
        }
        mCamera.autoFocus(autoFocusCallback);
    }

    public void onResume(){
        if (mCamera != null){
            mCamera.startPreview();
        }
    }

    public void onPause(){
        if (mCamera != null){
            mCamera.stopPreview();
        }
    }

    /**
     * 拍照回调接口
     */
    public interface TakePic{
        /**
         * 照片数据
         * @param data
         */
        void onTakePic(byte[] data);
    }

    Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            if (mCamera != null){
                mCamera.takePicture(null,null, new Camera.PictureCallback(){

                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        if (listener != null){
                            listener.onTakePic(data);
                        }
                    }
                });
            }
        }
    };
}
