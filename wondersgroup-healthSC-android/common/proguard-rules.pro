# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\tools\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# for retrofit
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontwarn **

#webview
-keepclassmembers class * extends android.webkit.WebChromeClient {
    public void openFileChooser(...);
    public void onShowFileChooser(...);
 }

-keep class * extends java.lang.annotation.Annotation { *; }

# 实体类，可能需要fastJson反射
-keep class com.wondersgroup.hs.healthcloud.common.entity.**{*;}

# R文件可能被第三方lib通过发射调用
-keepclassmembers class **.R$* {
    public static <fields>;
}

# fastjson
-keep class com.alibaba.fastjson.** { *; }
-keep class sun.misc.Unsafe { *; }
-keepattributes Signature,*Annotation*,InnerClasses

#eventbus
-keepclassmembers class ** {
    public void onEvent*(**);
}

#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}