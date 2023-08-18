# compose-desktop.pro
-dontoptimize
-dontobfuscate

-dontwarn kotlinx.**

-ignorewarnings

#noinspection ShrinkerUnresolvedReference
-keepclasseswithmembers public class com.github.purofle.nmsl.MainKt {
    public static void main(java.lang.String[]);
}
-keep class org.jetbrains.skia.** { *; }
-keep class org.jetbrains.skiko.** { *; }

# log4j2
-keep class org.apache.logging.log4j.** { *; }
-keep class org.slf4j.** { *; }
-keep class kotlin.reflect.** { *; }
-dontwarn org.apache.logging.log4j.**
