/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_iapppay_zbar_ZBarDecoder */

#ifndef _Included_com_iapppay_zbar_ZBarDecoder
#define _Included_com_iapppay_zbar_ZBarDecoder
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_iapppay_zbar_ZBarDecoder
 * Method:    decodeRaw
 * Signature: ([BII)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_iapppay_zbar_ZBarDecoder_decodeRaw
  (JNIEnv *, jobject, jbyteArray, jint, jint);

/*
 * Class:     com_iapppay_zbar_ZBarDecoder
 * Method:    decodeCrop
 * Signature: ([BIIIIII)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_iapppay_zbar_ZBarDecoder_decodeCrop
  (JNIEnv *, jobject, jbyteArray, jint, jint, jint, jint, jint, jint);

#ifdef __cplusplus
}
#endif
#endif
