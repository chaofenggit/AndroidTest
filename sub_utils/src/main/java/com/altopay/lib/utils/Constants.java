package com.altopay.lib.utils;

public class Constants {

    //==============================================================================================
    // -------------------------------------------通用---------------------------------------------
    public static final String PLATFORMID_FOLDER = ".wallet";
    public static final String PUBLIC_KEY = "PUBLIC_KEY";
    public static final String PRIVATE_KEY = "PRIVATE_KEY";
    public static final String REQUEST_METHOD_POST = "POST";
    public static final String CHARSET_UTF_8 = "UTF-8";
    public static final String ERR_MSG = "ErrMsg";
    public static final String ERR_CODE = "Code";
    public static final String MSG_KEY = "Msg";
    public static final String ERR_KEY = "Err";
    public static final int APP_UPDATE_TIME = 6 * 60 * 60 * 1000;  //版本检测间隔时间
    public static final String UPGRADE_VERSION_CODE = "upgradeVersionCode";
    public static final String RET_CODE_SUCCESS = "0";
    public static final String RET_CODE_KEY = "Code";
    public static final String DATA_KEY = "Data";
    public final static String LOCAL_RET_CODE_NETWORK_EXCEPTION = "-1";//网络异常,超时，没有应答包 等
    public final static String LOCAL_RET_CODE_UNCONNECTED = "-2";//网络连接失败
    public static final String Code_KEY = "code";
    public static final String msg_KEY = "msg";
    /**
     * 收银APP接口地址后缀
     */
    public static final String MODULE_APP_BASE = "/sdk/";

    public static final int HTTP_CONNECT_TIMEOUT = 10 * 1000;  //网络连接超时时间
    public static final int HTTP_READ_TIMEOUT = 10 * 1000;    //网络数据读取超时时间
    // -------------------------------------------通用---------------------------------------------
    //==============================================================================================


    // =============================================================================================
    // ------------------------------------------本地定义---------------------------------------------
    public static final String LOCAL_FROM_KEY = "From";
    public static final int LOCAL_FROM_WITHDRAW = 0;
    public static final int LOCAL_FROM_PAY = 2;
    public static final int LOCAL_FROM_FORGOT_PWD = 3;
    public static final int LOCAL_FROM_SIG_UP = 4;
    public static final int LOCAL_RESULT_CLOSE = 12;

    public static final String LOCAL_PAYMENT_REAL_NAME = "RealName";
    public static final String LOCAL_PAYMENT_PHONE = "Phone";
    public static final int LOCAL_PAYMENT_FORM_HOME = 0;
    public static final int LOCAL_PAYMENT_FROM_TRANSFER = 1;

    /**
     * 显示 由通知打开的 交易记录列表
     */
    public static final int FLAG_NOTIFICATION_TRANS_LIST_ACTIVITY = 999;
    public static final String FLAG_ACTIVITY_KEY = "FLAG_ACTIVITY_KEY";

    public static final String LOCAL_TRANS_MONEY_KEY = "transMoney";


    public static final int LOCAL_CLICK_FROM_SHOW_QR_SET_NUMBER = 1;
    public static final int LOCAL_CLICK_FROM_HOME_SCAN_QR = 2;
    public static final int LOCAL_CLICK_FROM_SHOW_PAY_QR = 3;
    public static final String LOCAL_WEB_VIEW_URL_KEY = "web_view_url_key";
    public static final String LOCAL_WEB_VIEW_TITLE = "web_view_title";

    public static final String LOCAL_QRCODE_SCAN_RESULT = "qrcode_scan_result";

    public static final String LOCAL_SHOW_FRAGMENT_KEY = "show_fragment";
    public static final String LOCAL_HOME_FRAGMENT = "homeFragment";
    public static final String LOCAL_LIFE_FRAGMENT = "lifeFragment";
    public static final String LOCAL_ME_FRAGMENT = "meFragment";

    public static final String LOCAL_POSITION_KEY = "Position";

    public static final String LOCAL_CODE_ID_KEY = "CodeID";
    public static final String LOCAL_pwd_KEY = "pwd";
    public static final String LOCAL_QUERY_CANCEL = "LOCAL_QUERY_CANCEL";//取消查询
    public static final int LOCAL_FROM_TRANSFER = 0;
    public static final int LOCAL_FROM_ME = 1;
    public static final int LOCAL_FROM_TOP_UP = 2;
    public static final String LOCAL_STARTS_WITH_8 = "8";
    public static final String LOCAL_STARTS_WITH_08 = "08";

    // ------------------------------------------本地定义--------------------------------------------
    //==============================================================================================



    //==============================================================================================
    // ------------------------------------------服务端定义------------------------------------------
    /**
     * 充值结果(0:等待充值,10:充值中,20:充值失败,30:充值成功)
     */
    public static final String APP_QUERY_PAY_RESULT_WAIT = "0";
    public static final String APP_QUERY_PAY_RESULT_PAGING = "10";
    public static final String APP_QUERY_PAY_RESULT_FAIL = "20";
    public static final String APP_QUERY_PAY_RESULT_SUCCESS = "30";

    /**
     * LP - 登录名+密码
     * LV - 登录名+登录凭证
     * OA - OAuth登录
     */
    public static final String LP_KEY = "LP";
    public static final String LV_KEY = "LV";
    /**
     * Cm: - 普通注册
     * Ph: - 手机号注册
     * Em：- 邮箱注册
     */
    public static final String PH_KEY = "Ph"; //手机号注册

    /**
     * UNSAFE: 非安全（未设置支付密码）;
     * SAFE:安全（设置支付密码）
     * LOCK:锁定（支付密码输错超过次数等）
     */
    public static final String UNSAFE_KEY = "UNSAFE";
    public static final String STATUE_SAFE_KEY = "SAFE";

    public static final String RET_CODE_PP001 = "PP001"; // 若用户登陆密码未被锁定，但当天累计输入错误小于5次
    public static final String RET_CODE_PP013 = "PP013"; // 用户登陆密码已被锁定，则当用户在锁定限制时间内进行登录
    public static final String RET_CODE_PP010 = "PP010"; // 手机号已注册
    public static final String RET_CODE_W200 = "W200";   // 验证码下发频繁

    public static final String CARD_BALANCE_NOT_ENOUGUT_CODE = "5012";//支付方式请求支付时支付失败pay order error
    public static final String PAY_PWD_ERROR_CODE = "A203";//支付密码错误
    public static final String PAY_PWD_TOKEN_ERROE_CODE = "A204";//支付Token过期
    public static final String PAY_PWD_TOKEN_INVALID_CODE = "A205";//Token失效
    public static final String PAY_NEW_PWD_CAN_NOT_SAME_NEW_CODE = "A207";//新旧密码不能相同
    public static final String PAY_PWD_ERROR_LOCKED_CODE = "A206";//密码被锁定
    public static final String CARD_PAY_FAILED_CODE = "A110";//已删除绑卡支付，卡支付失败
    // ------------------------------------------服务端定义------------------------------------------
    //==============================================================================================


    // ====================================================================
    // -----------------------------协议文档---------------------------------
    public static final String LOGIN_KEY = "Login";
    public static final String REG_KEY = "Reg";
    public static final String TYPE_KEY = "Type";
    public static final String NAME_KEY = "Name";
    public static final String LOGIN_PASSWORD_KEY = "Pwd";
    public static final String REGISTER_VCODE_KEY = "VCode";

    public static final String LNAME_KEY = "LName";
    public static final String NNAME_KEY = "NName";
    public static final String UT_KEY = "UT";
    public static final String CPNAME_KEY = "CPName";
    public static final String UID_KEY = "UID";
    public static final String TID_KEY = "TID";
    public static final String TIMES_TAMP_KEY = "Timestamp";
    public static final String AVATAR_KEY = "Avatar";
    public static final String VOUCHER_KEY = "Voucher";
    public static final String SID_KEY = "SID";
    public static final String USER_KEY = "User";
    public static final String AUTH_KEY = "Auth";
    public static final String PRICE_KEY = "Price";
    public static final String RESULT_KEY = "Result";
    public static final String REASON_KEY = "Reason";
    public static final String ID_KEY = "Id";
    public static final String URL_KEY = "Url";
    public static final String URL1_KEY = "URL";
    public static final String URI_KEY = "URI";
    public static final String AVC_KEY = "AVC";
    public static final String GVC_KEY = "GVC";
    public static final String NOPWD_LIMIT_KEY = "NoPwdLimit";

    public static final String CLOSE_KEY = "Close";
    public static final String FEE_KEY = "Fee";
    public static final String LIMIT_KEY = "Limit";
    public static final String LIMIT_MIN_KEY = "min";
    public static final String LIMIT_MAX_KEY = "max";
    public static final String ST_KEY = "St";
    public static final String BID_KEY = "BID";
    public static final String BANK_KEY = "Bank";
    public static final String ICON_KEY = "Icon";
    public static final String CARD_KEY = "Card";
    public static final String LINK_KEY = "Link";
    public static final String ACCOUNT_KEY = "Account";
    public static final String ACCOUNT_NO_KEY = "AccountNo";
    public static final String BIND_KEY = "Bind";
    public static final String QR_KEY = "QR";
    public static final String ADD_BANK_ACCOUNT = "BankAccount";
    public static final String PROMPT_KEY = "Prompt";
    public static final String PAYPWD_KEY = "PayPwd";
    public static final String BANK_ACCOUNT_NO_KEY = "BankAccNo";
    public static final String BANK_CODE_KEY = "BankCode";
    public static final String TT_KEY = "TT";
    public static final String TTYPE_KEY = "TType";
    public static final String OT_KEY = "OT";
    public static final String ID_PAY_TYPE_KEY = "ID";
    public static final String RECHR_RATE_KEY = "RechrRate";
    public static final String PAY_LIMIT_KEY = "PayLimit";
    public static final String PAY_EX_KEY = "PayEx";
    public static final String PAY_INFO_KEY = "PayInfo";
    public static final String PAY_AUTH_KEY = "PayAuth";
    public static final String BLOCK_KEY = "Block";
    public static final String DESC_KEY = "Desc";
    public static final String PAY_TYPES_KEY = "PayTypes";
    public static final String APP_PKG_KEY = "AppPkg";
    public static final String NET_TYPE_KEY = "NetType";
    public static final String CPORDER_KEY = "CPOrder";
    public static final String CFGVER_KEY = "CfgVer";
    public static final String GNAME_KEY = "GName";
    public static final String MER_KEY = "Mer";
    public static final String APP_ID_KEY = "AppId";
    public static final String CFG_VERSION_KEY = "cfgversion";//版本号
    public static final String USER_ICON_KEY = "userIcon";
    public static final String PAT_TYPE_ICON_KEY = "pTypeIcon";
    public static final String BANK_ICON_KEY = "bankIcon";
    public static final String GUI_KEY = "GUI";
    public static final String GUI_K = "K";
    public static final String GUI_V = "V";
    public static final String TITLE_KEY = "title";
    public static final String url_KEY = "url";
    public static final String TRANS_KEY = "Trans";
    public static final String CLIENT_CFG_KEY = "ClientCfg";
    public static final String NOTIFY_KEY = "Notify";

    public static final String PAY_ORDER_KEY = "PayOrder";
    public static final String PAY_PARAM_KEY = "PayParam";
    public static final String INVOKE_KEY = "Invoke";
    public static final String CHANNEL_KEY = "Channel";
    public static final String CHANNEL_ID_KEY = "ChannelID";
    public static final String HIDDEN_FLAG_KEY = "HiddenFlag";
    public static final String APP_RESP_KEY = "AppResp";
    public static final String PAY_KEY = "Pay";
    public static final String RECHR_KEY = "Rechr";
    public static final String RECHR_TYPES_KEY = "RechrTypes";
    public static final String SELECTED_KEY = "Selected";
    public static final String LANGUAGE_KEY = "language";
    public static final String TO_KEY = "To";
    public static final String AMOUNT_KEY = "Amount";
    public static final String SUBJECT_KEY = "Subject";
    public static final String FLAGS_KEY = "Flags";
    public static final String CUR_KEY = "Cur";
    public static final String CURRENCY_KEY = "Currency";
    public static final String TARGET_KEY = "Target";
    public static final String REMARK_KEY = "Remark";
    public static final String AMT_KEY = "Amt";
    public static final String TRANS_CUR_KEY = "TransCur";
    public static final String TRANS_AMT_KEY = "TransAmt";
    public static final String EX_RATE_KEY = "ExRate";
    public static final String OS_KEY = "OS";
    public static final String OS_Ver_KEY = "OSVer";
    public static final String OS_ANDROID = "Android";

    public static final String PAY_TYPE_KEY = "PayType";
    public static final String PAY_TYPE_NAME_KEY = "PayTypeName";
    public static final String STATUS_KEY = "Status";
    public static final String SCHEME_KEY = "Scheme";
    public static final String BALANCE_KEY = "Balance";

    public static final String TEL_KEY = "Tel";
    public static final String VERIFY_CODE_KEY = "VerifyCode";
    public static final String V_CODE_KEY = "Vcode";
    public static final String WAIT_KEY = "Wait";
    public static final String REMAIN_KEY = "Remain";

    public static final String TOKEN_KEY = "Token";
    public static final String DEVICE_BRAND_KEY = "DeviceBrand";
    public static final String IMSI_KEY = "IMSI";
    public static final String IMEI_KEY = "IMEI";
    public static final String ANDROID_ID_KEY = "AndroidID";
    public static final String MAC_KEY = "Mac";
    public static final String MODEL_KEY = "Model";
    public static final String TINFO_KEY = "TInfo";
    public static final String APP_VER = "AppVer";
    // -----------------------------协议文档---------------------------------
    // ====================================================================

}