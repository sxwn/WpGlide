/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_xiaowei_ndk_MainActivity */

#ifndef _Included_com_xiaowei_ndk_MainActivity
#define _Included_com_xiaowei_ndk_MainActivity
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_xiaowei_ndk_MainActivity
 * Method:    getText
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_xiaowei_ndk_MainActivity_getText
  (JNIEnv *env, jobject obj){
    //写的是c/c++代码
    //ndk与java并没有太大关系,都是在玩C
    return (*env) ->NewStringUTF(env,"hello ndk!");
}

#ifdef __cplusplus
}
#endif
#endif
