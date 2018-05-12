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
-dontwarn **

#webview
-keepclassmembers class * extends android.webkit.WebChromeClient {
    public void openFileChooser(...);
    public void onShowFileChooser(...);
 }
 -keep class com.wondersgroup.hs.healthcloud.patient.module.JsCallBack {*;}

-keep class * extends java.lang.annotation.Annotation { *; }

# 实体类，可能需要fastJson反射
-keep class com.wondersgroup.hs.healthcloud.common.entity.**{*;}
-keep class com.wonders.health.venus.open.user.entity.**{*;}

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

#科大讯飞
-keep class com.iflytek.**{*;}
#百度地图
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}

#Trinity统计
-keep class com.wonders.bud.budtrinity.** { *; }
-keep class org.openudid.** { *; }
#个推
-dontwarn com.igexin.**
-keep class com.igexin.** {*;}
# shareSDK
-keep class com.tencent.**{*;}
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class m.framework.**{*;}

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#友盟
-keep public class * extends com.umeng.**
-keep class com.umeng.** { *; }

#七牛
-keep class com.qiniu.** {*;}
-keep class com.qiniu.**{public <init>();}
-ignorewarnings

#环信
-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**

#统一支付
-keep class cn.wd.** {*;}
-keep class com.google.**{*;}
-keep class com.igexin.**{*;}
-keep class cn.wanda.** {*;}
-keep class com.alipay.**{*;}
-keep  public class com.unionpay.** {
	public <methods>;
}
