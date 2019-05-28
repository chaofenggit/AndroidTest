package com.xiaoniu.zbartest.decode;

import android.graphics.Bitmap;
import android.util.Log;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;


/**
 * 创建时间:16/4/8 下午11:22
 * 描述:Zbar解析二维码图片
 */
public class ZbarDecoder {
    /**
     * 识别相册图片
     * @param path
     * @return
     */
    public String decodeQRCode(String path) {
        Bitmap scanBitmap = DecoderUtil.getLocalBitmap(path);
        return decodeQRCode(scanBitmap) ;
    }


    /**
     * @param mBitmap
     * @return
     */
    public String decodeQRCode(Bitmap mBitmap) {
        Log.d(" ", "------------开始执行【 ZBar 解码 】------------");
        byte[] data = DecoderUtil.getYUV420sp(mBitmap.getWidth(), mBitmap.getHeight(), mBitmap);

//        //调用Zbar 解码
//        com.dtr.zbar.build.ZBarDecoder mZBarDecoder = new com.dtr.zbar.build.ZBarDecoder();
//        String decodeRawStr =  mZBarDecoder.decodeRaw(data, mBitmap.getWidth(), mBitmap.getHeight());
//        DecoderUtil.gcBitmap(mBitmap);
//
//        return decodeRawStr ;

        ImageScanner mImageScanner = new ImageScanner();
        mImageScanner.setConfig(0, Config.X_DENSITY, 3);
        mImageScanner.setConfig(0, Config.Y_DENSITY, 3);
        Image barcode = new Image(mBitmap.getWidth(), mBitmap.getHeight(), "Y800");
        barcode.setData(data);
        int result = mImageScanner.scanImage(barcode);
        String resultStr = null;

        if (result != 0) {
            SymbolSet syms = mImageScanner.getResults();
            for (Symbol sym : syms) {
                resultStr = sym.getData();
            }
        }
        return resultStr;
    }


    /**
     * @return
     */
    public String decodeQRCode(byte[] rotatedData, int width, int height, int cropRectLeft, int cropRectTop, int cropRect_W, int cropRect_H){
        Log.d(" ", "------------开始执行【 解码 】------------");
        //调用Zbar 解码
//        com.dtr.zbar.build.ZBarDecoder mZBarDecoder = new com.dtr.zbar.build.ZBarDecoder();
//        String result = mZBarDecoder.decodeCrop(rotatedData, width, height, cropRectLeft, cropRectTop , cropRect_W, cropRect_H);

//        return result;

        ImageScanner mImageScanner = new ImageScanner();
        mImageScanner.setConfig(0, Config.X_DENSITY, 3);
        mImageScanner.setConfig(0, Config.Y_DENSITY, 3);
        Image barcode = new Image(width, height, "Y800");
        barcode.setData(rotatedData);
        barcode.setCrop(cropRectLeft, cropRectTop, cropRect_W,cropRect_H);

        int result = mImageScanner.scanImage(barcode);
        String resultStr = null;

        if (result != 0) {
            SymbolSet syms = mImageScanner.getResults();
            for (Symbol sym : syms) {
                resultStr = sym.getData();
            }
        }
        return resultStr;
    }

}