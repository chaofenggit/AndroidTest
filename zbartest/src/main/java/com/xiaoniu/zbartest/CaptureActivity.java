package com.xiaoniu.zbartest;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xiaoniu.zbartest.camera.CameraManager;
import com.xiaoniu.zbartest.decode.MainHandler;

import java.io.IOException;
import java.lang.reflect.Field;


public class CaptureActivity extends Activity implements SurfaceHolder.Callback {

        private static final int REQUEST_READ_STORAGE_PERMISSION = 100;

        private static final String TAG = "CaptureActivity";

        /**
         * 相册图片以file:///开头
         */
        private static final String START_WITH_FILE = "file:///";

        protected static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
        private static final long ANIMATION_DURATION_TIME = 1800;
        protected MainHandler mainHandler;
        protected SurfaceHolder mHolder;

        protected CameraManager mCameraManager;

        protected SurfaceView scanPreview;
        private RelativeLayout scanContainer;
        protected RelativeLayout scanCropView;
        private ImageView scanLine;

        protected boolean isHasSurface = false;

//        private String picturePath;
//        private DecodeAsyncTask myAsyncTask;
        private int titleBarHeight = 0;

//        protected LoadingDialog loadingDialog;

        public Handler getHandler() {
            return mainHandler;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_capture);
            initView();
        }

        private void initView() {
            scanPreview = findViewById(R.id.capture_preview);
            scanContainer = findViewById(R.id.capture_container);
            scanCropView = findViewById(R.id.capture_crop_view);
            scanLine = findViewById(R.id.capture_scan_line);
            isHasSurface = false;
//            TextView tv_describe = findViewById(R.id.tv_describe);
//            tv_describe.setText(getString(R.string.Scan_B0));
        }

        private void initScanLineAnimation() {
            TranslateAnimation animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT, 0.0f,
                    Animation.RELATIVE_TO_PARENT, 0.0f,
                    Animation.RELATIVE_TO_PARENT, -0.03f,
                    Animation.RELATIVE_TO_PARENT, 1.0f);
            animation.setDuration(ANIMATION_DURATION_TIME);
            animation.setRepeatCount(-1);
            animation.setRepeatMode(Animation.RESTART);
            scanLine.startAnimation(animation);
        }

        @Override
        protected void onResume() {
            super.onResume();
            reInitCamera();
        }

        @Override
        public void onPause() {
            gcCamera();
            super.onPause();
            clearScanLine();
        }

        /**
         * region 初始化和回收相关资源
         *
         * @param surfaceHolder
         */
        private void initCamera(SurfaceHolder surfaceHolder) {
            mainHandler = null;
            try {
                if (mCameraManager == null) {
                    mCameraManager = new CameraManager(getApplication());
                }
                mCameraManager.openDriver(surfaceHolder);
                if (mainHandler == null) {
                    mainHandler = new MainHandler(this, mCameraManager);
                }
            } catch (IOException ioe) {
                Log.e(TAG, "相机被占用: " + ioe.toString());
            } catch (RuntimeException e) {
                e.printStackTrace();
                Log.e(TAG, "Unexpected error initializing camera");
//                openAppSettingDetails(getString(R.string.DialogMsg_B0));
            }
        }

        /**
         * 扫码结果处理
         *
         * @param scanResult
         */
        public void handleScanResult(String scanResult){
            Toast.makeText(CaptureActivity.this, scanResult, Toast.LENGTH_LONG).show();
        }

        //释放
        private void gcCamera() {
            if (null != mainHandler) {
                //关闭聚焦,停止预览,清空预览回调,quit子线程looper
                mainHandler.quitSynchronously();
                mainHandler = null;
            }
            //关闭相机
            if (mCameraManager != null) {
                mCameraManager.closeDriver();
                mCameraManager = null;
            }
        }

        protected void reInitCamera() {
            initScanLineAnimation();
            mHolder = scanPreview.getHolder();

            if (isHasSurface) {
                initCamera(mHolder);
            } else {
                mHolder.addCallback(this);
                mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            }
        }

        //region  初始化截取的矩形区域
        public Rect initCrop() {
            int cameraWidth = 0;
            int cameraHeight = 0;
            if (null != mCameraManager) {
                cameraWidth = mCameraManager.getCameraResolution().y;
                cameraHeight = mCameraManager.getCameraResolution().x;
            }

            /** 获取布局中扫描框的位置信息 */
            int[] location = new int[2];
            scanCropView.getLocationInWindow(location);

            int cropLeft = location[0];
            /**
             * 计算扫描框左上角距离父布局的距离 = 扫描框左上角的Y坐标 - 状态栏的高度 - 布局中状态栏的高度
             */
            int cropTop = location[1] - getStatusBarHeight() - titleBarHeight;

            int cropWidth = scanCropView.getWidth();
            int cropHeight = scanCropView.getHeight();

            /** 获取布局容器的宽高 */
            int containerWidth = scanContainer.getWidth();
            int containerHeight = scanContainer.getHeight();

            /** 计算最终截取的矩形的左上角顶点x坐标 */
            int x = cropLeft * cameraWidth / containerWidth;
            /** 计算最终截取的矩形的左上角顶点y坐标 */
            int y = cropTop * cameraHeight / containerHeight;

            /** 计算最终截取的矩形的宽度 */
            int width = cropWidth * cameraWidth / containerWidth;
            /** 计算最终截取的矩形的高度 */
            int height = cropHeight * cameraHeight / containerHeight;

            /** 生成最终的截取的矩形 */
            return new Rect(x - 10, y - 10, width + x + 10, height + y + 10);
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

        //region SurfaceHolder Callback 回调方法
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (holder == null) {
                Log.e(TAG, "*** 没有添加SurfaceHolder的Callback");
            }
            if (!isHasSurface) {
                isHasSurface = true;
                initCamera(holder);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.e(TAG, "surfaceChanged---->");
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            isHasSurface = false;
        }

        /**
         * 检查sd卡的读权限，做相应的处理
         */
        private void checkStoragePer() {
            if (Build.VERSION.SDK_INT < 23 || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openAlbum();

            } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                openAppSettingDetails(getString(R.string.DialogMsg_A0));

            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE_PERMISSION);
            }
        }

        private void openAlbum() {
            //其他手机url路径：content://media/external/images/media/299
            //华为手机获取的路径：content://com.android.providers.media.documents/document/image%3A100595
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY);
            }
        }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
//            onBack();
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            //remove SurfaceCallback
            if (!isHasSurface && scanCropView != null) {
                scanPreview.getHolder().removeCallback(this);
            }
        }

//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//            super.onActivityResult(requestCode, resultCode, data);
//            if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
//                if (null != data) {
//                    if (data.getDataString().startsWith(START_WITH_FILE)) {
//                        picturePath = data.getDataString().replace(START_WITH_FILE, "");
//                        if (TextUtils.isEmpty(picturePath)) {
//                            Log.d(TAG, "读取相册图片失败");
////                            showToast(getString(R.string.Toast_O0));
//                            return;
//                        }
//                    } else {
//                        Uri uri = data.getData();
//                        String[] proj = {MediaStore.Images.Media.DATA};
//                        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
//                        if (cursor == null) {
////                            showToast(getString(R.string.Toast_O0));
//                            return;
//                        }
//                        if (cursor.moveToFirst()) {
//                            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                            picturePath = cursor.getString(columnIndex);
//                            if (TextUtils.isEmpty(picturePath)) {
//                                Log.d(TAG, "读取相册图片失败");
//                            }
//                            Log.d(TAG, "读取相册图片path：" + picturePath);
//                        }
//                        cursor.close();
//                    }
//                    // 执行解码任务
//                    onExecuteDecode();
//                } else {
////                    showToast(getString(R.string.Toast_O0));
//                }
//            }
//        }

//        private void onExecuteDecode() {
//            if (myAsyncTask != null) {
//                if (myAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
//                    myAsyncTask.cancel(true);
//                }
//                myAsyncTask = null;
//            }
//            myAsyncTask = new DecodeAsyncTask(showLoading(""));
//            myAsyncTask.execute();
//        }

//        private class DecodeAsyncTask extends AsyncTask<Void, Void, String> {
//
////            LoadingDialog mLoading;
//
//            DecodeAsyncTask(LoadingDialog loadingDialog) {
//                this.mLoading = loadingDialog;
//            }
//
//            @Override
//            protected String doInBackground(Void... voids) {
//                return new DecoderSDK().decodeQRCode(picturePath);
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                closeLoading(mLoading);
//                if (TextUtils.isEmpty(result)) {
//                    showToast(getString(R.string.Toast_O0));
//                } else {
//                    LogUtil.d(TAG, "识别结果： " + result);
//                    // 识别二维码图片
//                    gcCamera();
//                    clearScanLine();
//                    handleScanResult(result);
//                }
//            }
//        }
//

        public void showToast(String msg){
            Toast.makeText(CaptureActivity.this, msg, Toast.LENGTH_LONG).show();
         }

        //region 扫描结果
        public void checkResult(final String result) {
            Log.e(TAG, "扫码result: " + result);
            if (TextUtils.isEmpty(result)) {
                showToast("空");
                reInitCamera();
            } else {
                if (!isFinishing()) {
                    // 扫码结果处理
                    gcCamera();
                    clearScanLine();
                    handleScanResult(result);
//                    if (myAsyncTask != null) {
//                        myAsyncTask.cancel(true);
//                    }
                }
            }
        }

        private void clearScanLine() {
            if (scanLine != null) {
                scanLine.clearAnimation();
                scanLine.setVisibility(View.GONE);
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == REQUEST_READ_STORAGE_PERMISSION && grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();

            } else if (requestCode == REQUEST_READ_STORAGE_PERMISSION) {
//                openAppSettingDetails(getString(R.string.DialogMsg_A0));
            }
        }

//        public LoadingDialog showLoading(String str) {
//            loadingDialog = null;
//            loadingDialog = LoadingDialog.showDialogLoading(this, str);
//            return loadingDialog;
//        }

//        protected void showTipDialog(String msg) {
//            new CommonDialog
//                    .Builder(this)
//                    .setMessage(msg)
//                    .setCancelable(false)
//                    .setPositiveButton(getString(R.string.DialogButton_B0), new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                            reInitCamera();
//                        }
//                    })
//                    .show();
//        }

    }