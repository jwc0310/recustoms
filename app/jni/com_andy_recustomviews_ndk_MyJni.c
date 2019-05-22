//
// Created by Administrator on 2017/4/14.
//

#include <stdlib.h>
#include <time.h>
#include "com_andy_recustomviews_ndk_MyJni.h"
#include "string.h"
#include "android/log.h"
#include <stddef.h>
// log标签
#define  TAG    "hello_load"
// 定义info信息
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)
// 定义debug信息
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
// 定义error信息
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)

//#ifndef _Included_com_andy_recustomviews_ndk_MyJni
//#define _Included_com_andy_recustomviews_ndk_MyJni
//#ifdef __cplusplus
//extern "C" {
//#endif

//定义随机数产生宏， 表示产生0~x之间的随机数
#define random(x) (rand()%x)

jstring JNICALL Java_com_andy_recustomviews_ndk_MyJni_displayJni
  (JNIEnv *env, jobject obj){
  LOGE("displayJni");
      return (*env)->NewStringUTF(env, "hello andy jni world");
}

jstring JNICALL Java_com_andy_recustomviews_ndk_MyJni_xyUpdate
  (JNIEnv *env, jobject obj, jstring str1, jstring str2){

    //将jstring转换成const char*指针， 使用const修饰符表示内容不能修改
        const char *c1 = (*env)->GetStringUTFChars(env, str1, NULL);
        const char *c2 = (*env)->GetStringUTFChars(env, str2, NULL);

        //计算新字符串的长度
        int size = strlen(c1)+strlen(c2);
        //创建一个新的字符串，这里长度+1是为了使字符串有结尾标记'\0'
        //char [] n_char = new char[size+1];
        char *n_char = (char*) malloc((size+1) * sizeof(char));
    //利用C标准库提供的字符串操作方法对字符串进行连接，这里需要include"string.h"头文件
        strcpy(n_char, c1);
        strcat(n_char, c2);

        //将生成的新字符串转换成utf的jstring
        jstring rs = (*env)->NewStringUTF(env, n_char);

        //删除刚刚分配的内存， 避免引起内存泄露
        free(n_char);
//        delete[] n_char;

    //通知JVM虚拟机Native代码不在持有字符串的引用，说明白点,就是告诉虚拟机我不使用它了，你可以回收了。
    //因为在JVM中如果对象被引用，那么对象将不会被回收。
    //这里为什么要传递jstring和生成的char*呢？是因为char*有可能是jstring的拷贝，如果是拷贝，那么char*就应该被删除。

(*env)->ReleaseStringUTFChars(env, str1, c1);
(*env)->ReleaseStringUTFChars(env, str2, c2);
LOGE("xyupdate");

    return rs;

}

jintArray JNICALL Java_com_andy_recustomviews_ndk_MyJni_getIntArray
  (JNIEnv *env, jobject obj, jint size){

    //用时间变量初始化随机数产生器
    srand((int)time(0));
//    jint* rs = new jint[size];
    jint* rs = (jint *) malloc(size * sizeof(int));
    for(int i=0; i<size;i++){
    //调用宏产生0~100随机数
        rs[i]=random(100);
    }
    //通过JNIEnv的NewIntArray方法new一个jintArray对象
    jintArray array=(*env)->NewIntArray(env, size);
    //把产生的随机数复制给jintArray
(*env)->SetIntArrayRegion(env, array, 0, size, rs);
LOGE("getIntArray");
    return array;
  }

jstring JNICALL Java_com_andy_recustomviews_ndk_MyJni_strConcat
    (JNIEnv *env, jobject obj, jstring str1, jstring str2){

    //将jstring转换成const char*指针，使用const修饰符表示其内容不可被修改
            const char* c1=(*env)->GetStringUTFChars(env, str1, NULL);
            const char* c2=(*env)->GetStringUTFChars(env, str2, NULL);
            //计算新字符串的长度
            int size=strlen(c1)+strlen(c2);
            //创建一个新的字符串，这里长度+1是为了使字符串有结尾标记'\0'
            //char * n_char=new char[size+1];
            char* n_char = (char *) malloc((size+1) * sizeof(char*));
            //利用C标准库提供的字符串操作方法对字符串进行连接，这里需要include"string.h"头文件
            strcpy(n_char,c1);
            strcat(n_char,c2);
            //将生成的新字符串转换成UTF的jstring
            jstring rs=(*env)->NewStringUTF(env, n_char);
            //删除刚刚分配的内存 避免引起内存泄漏
            free(n_char);
            //delete [] n_char;
            //通知JVM虚拟机Native代码不在持有字符串的引用，说明白点,就是告诉虚拟机我不使用它了，你可以回收了。
            //因为在JVM中如果对象被引用，那么对象将不会被回收。
            //这里为什么要传递jstring和生成的char*呢？是因为char*有可能是jstring的拷贝，如果是拷贝，那么char*就应该被删除。
            (*env)->ReleaseStringUTFChars(env, str1,c1);
            (*env)->ReleaseStringUTFChars(env, str2,c2);
            LOGE("strConcat");
            return rs;
}

jobject JNICALL Java_com_andy_recustomviews_ndk_MyJni_newBean
  (JNIEnv *env, jobject obj, jstring msg, jint what){
    //先找到class
    jclass bean_clz = (*env)->FindClass(env, "com/andy/recustomviews/ndk/Bean");
    //在实际应用中应该确保你的class,method, field存在.减少此类判断
    if(NULL == bean_clz){
        LOGE("can't find class");
        return NULL;
    }
    //获取构造函数，构造函数的返回值是void，因此这里方法签名最后为V
    jmethodID bean_init=(*env)->GetMethodID(env, bean_clz, "<init>", "(Ljava/lang/String;I)V");
    if(bean_init == NULL){
        LOGE("can't find init function");
        return NULL;
    }
    //然后调用构造函数获得bean
    jobject bean = (*env)->NewObject(env, bean_clz, bean_init, msg, what);
    LOGE("new Bean");
    return bean;

  }

//
//#ifdef __cplusplus
//}
//#endif
//#endif

