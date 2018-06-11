package com.altopay.lib.utils.Cryptor;

import com.altopay.lib.utils.AltopayConfig;
import com.altopay.lib.utils.RSAHelper;
import com.altopay.lib.utils.logutil.LogUtil;

public class AesKeyCryptor {
    /**
     * 加密当前密码
     * <p>1、前段收集到用户的密码（包括登录密码和支付密码）后，需要使用平台提供的公钥进行加密，并按照固定格式组装密码的密文串。</p>
     * <p>2、最终密码串=!!0001+rsa("密码明文"，公钥)，其中!!0001是6位特定标识，0001是公钥编号。特定标识和公钥由基础平台提供。
     *                !!是固定两位，0000固定四位，这四位要替换成rsa秘钥的id，不够四位前面补0
     * <br>例如：!!0001XXXX，其中XXXX为密码密文</br>
     * <p>3、最终密码串由sdk完成组装，业务层服务端透传给基础平台。</p>
     * <p>4、基础平台按格式对密码进行解密后存储。</p>
     *
     * @param pwd 需要加密的密码明文
     * @return 经过rsa加密的密码
     */
    public static String encodePayPwd(String pwd) {
        String keyNo = "";
        String key = RSAConfig.instance().getPublicKey_pwd_pay();
        String rsaPwd = "";
        try {
            rsaPwd = RSAHelper.encryptByPublicKey(pwd, key);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("AesKeyCryptor", "encodePayPwd exception:" + e.toString());
            rsaPwd = pwd;
        }
        return keyNo + rsaPwd;
    }

    public static String encodeLoginPwd(String pwd) {
        String keyNo = "";
        String key = RSAConfig.instance().getPublicKey_pwd_login();
        String rsaPwd = "";
        try {
            rsaPwd = RSAHelper.encryptByPublicKey(pwd, key);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("AesKeyCryptor", "encodePayPwd exception:" + e.toString());
            rsaPwd = pwd;
        }
        return keyNo + rsaPwd;
    }
}
