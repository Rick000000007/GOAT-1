############################################################
# GOATX - Production ProGuard / R8 Rules
############################################################

###############################
# 1. Keep Application Classes
###############################

# Keep MainActivity
-keep class com.goatx.MainActivity { *; }

# Keep all classes in goatx package (safe for terminal engine)
-keep class com.goatx.** { *; }

###############################
# 2. Keep JNI Native Methods
###############################

# Prevent native methods from being removed or renamed
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep Native bridge classes fully
-keep class com.goatx.nativebridge.** { *; }

###############################
# 3. Prevent Kotlin Metadata Stripping
###############################

-keep class kotlin.Metadata { *; }
-keep class kotlin.** { *; }

###############################
# 4. Keep InputConnection & IME
###############################

-keep class android.view.inputmethod.** { *; }
-keep class * extends android.view.inputmethod.BaseInputConnection { *; }

###############################
# 5. Keep Enum Classes (ANSI states)
###############################

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

###############################
# 6. Keep Logging (Optional)
###############################

-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

###############################
# 7. Keep CMake / Native Lib Loader
###############################

-keep class com.goatx.nativebridge.NativeLoader { *; }

###############################
# 8. Prevent Reflection Issues
###############################

-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes EnclosingMethod

###############################
# 9. Optimize But Keep Safety
###############################

-dontwarn kotlin.**
-dontwarn org.jetbrains.annotations.**

###############################
# 10. Shrink but Keep Engine Stability
###############################

# Allow R8 to optimize but never remove critical engine
-keepclassmembers class com.goatx.terminal.** {
    *;
}

############################################################
# END OF GOATX PROGUARD CONFIG
############################################################