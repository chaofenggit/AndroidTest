package com.xiaoniu.fragmentactivityresult.util;

import android.content.Intent;

/**
 * @author xiaoniu
 * @date 2019/1/24.
 */

public interface ActivityResultListener {

    /**
     * 结果回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
