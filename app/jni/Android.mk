LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE := myjni
LOCAL_LDLIBS:=-L$(SYSROOT)/usr/lib -llog
LOCAL_SRC_FILES := com_andy_recustomviews_ndk_MyJni.c
include $(BUILD_SHARED_LIBRARY)