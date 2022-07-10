# proguard-rules.pro
-dontoptimize
-dontobfuscate

-dontwarn kotlinx.**

-ignorewarnings

-keepclasseswithmembers public class com.github.purofle.nmsl.MainKt {
    public static void main(java.lang.String[]);
}
-keep class org.jetbrains.skia.** { *; }
-keep class org.jetbrains.skiko.** { *; }