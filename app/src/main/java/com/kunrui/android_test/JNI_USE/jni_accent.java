package com.kunrui.android_test.JNI_USE;

public class jni_accent {
    static {
        System.loadLibrary("native-lib");
    }

    public native String stringFromJNI();

    public native String myNativeJNIMethodWithLog();

    public native String sayHello();
}
