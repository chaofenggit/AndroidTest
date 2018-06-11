package com.altopay.lib.utils.network;

import android.text.TextUtils;

import com.altopay.lib.utils.Constants;
import com.altopay.lib.utils.logutil.LogUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class AltoPayHttpUtil {

    private static final String TAG = AltoPayHttpUtil.class.getSimpleName();
    private static final int bufSize = 1024 * 8;

    /**
     * POST请求
     *
     * @param urlString
     * @param paramBytes
     * @param encrypt    是否加密
     * @return
     */
    public static byte[] sendPostRequestCallbackByte(String urlString, byte[] paramBytes, boolean encrypt, String userAgent) throws Exception {
        byte[] result = null;
        HttpURLConnection httpURLConnection;
        if (LogUtil.outDebugIsExist()) {
            urlString = urlString + "?_debug";
        }
        LogUtil.i(TAG, " altopay request link: " + urlString);

        trustAllHosts();//解决~~https请求时，会报出SSL握手异常

        URL url = new URL(urlString);
        httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod(Constants.REQUEST_METHOD_POST);
        httpURLConnection.setUseCaches(false);//不使用缓存
        httpURLConnection.setConnectTimeout(Constants.HTTP_CONNECT_TIMEOUT);//连接服务器超时（单位：毫秒）
        httpURLConnection.setReadTimeout(Constants.HTTP_READ_TIMEOUT);//从服务器读取数据超时（单位：毫秒）
        httpURLConnection.setDoInput(true);// 设置运行输入
        httpURLConnection.setDoOutput(true);// 设置运行输出
        //设定传送的内容类型是可序列化的java对象
        // (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
        httpURLConnection.addRequestProperty("Content-type", "application/json");
        httpURLConnection.addRequestProperty("charset", "UTF-8");
        httpURLConnection.addRequestProperty("Accept-Encoding", "gzip, deflate");//HttpURLConnection 默认使用gzip压缩和自动解压缩
//            httpURLConnection.setRequestProperty("Accept-Encoding", "identity");//屏蔽gzip压缩
        if (!TextUtils.isEmpty(userAgent)) {
            httpURLConnection.setRequestProperty("User-Agent", userAgent);
        }

        if (encrypt) {//是否需要加密
            httpURLConnection.addRequestProperty("X-IAPPPAY-Shttp", "Y");
        }

        // 将请求的数据写入输出流中
        OutputStream os = httpURLConnection.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        bos.write(paramBytes);
        bos.flush();// 刷新对象输出流，将任何字节都写入潜在的流中
        bos.close();
        os.close();

        int responseCode = httpURLConnection.getResponseCode();
        LogUtil.i(TAG, "httpURLConnection.getResponseCode() POST:" + responseCode);
        if (HttpURLConnection.HTTP_OK == responseCode) {
            InputStream inputStream = httpURLConnection.getInputStream();
            if ("gzip".equals(httpURLConnection.getHeaderField("Content-Encoding"))) {
                inputStream = new GZIPInputStream(new BufferedInputStream(inputStream));
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int len = -1;
            byte[] buffer = new byte[bufSize];
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            result = outputStream.toByteArray();
            outputStream.close();
            inputStream.close();
        }
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }

        return result;
    }

    /**
     * GET 请求
     * @param requestUrl
     * @return
     * @throws Exception
     */
    public static String sendGetRequestCallbackString(String requestUrl) throws Exception {

        trustAllHosts();

        //建立连接
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setDoOutput(false);
        connection.setDoInput(true);
        connection.setUseCaches(false);

        connection.setConnectTimeout(Constants.HTTP_CONNECT_TIMEOUT);
        connection.setReadTimeout(Constants.HTTP_READ_TIMEOUT);

        connection.connect();

        //获取响应状态
        int responseCode = connection.getResponseCode();
        LogUtil.i(TAG, "httpURLConnection.getResponseCode() GET:" + responseCode);

        if (HttpURLConnection.HTTP_OK == responseCode) { //连接成功
            //当正确响应时处理数据
            StringBuffer buffer = new StringBuffer();
            String readLine;
            BufferedReader responseReader;
            //处理响应流
            responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((readLine = responseReader.readLine()) != null) {
                buffer.append(readLine).append("\n");
            }
            responseReader.close();
            LogUtil.d(TAG, "GET Result: "+buffer.toString());
            //JSONObject result = new JSONObject(buffer.toString());
            return buffer.toString();
        }
        if (connection != null) {
            connection.disconnect();
        }
        return null;
    }


    private static void trustAllHosts() {
        /** 学习地址 http://blog.csdn.net/james_liao3/article/details/52587019 */
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }
        } };
        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
