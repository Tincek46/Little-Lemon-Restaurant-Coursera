# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# --- Kotlinx Serialization ---
# Keep serializers and serializable classes
-if @kotlinx.serialization.Serializable class * {
    @kotlinx.serialization.Serializer <methods>;
}
-keepclassmembers class * {
    @kotlinx.serialization.descriptors.SerialDescriptor <fields>;
}
# Keep companions of serializable classes
-keep class * {
    @kotlinx.serialization.Serializable companion object;
}
# Keep generated serializers
-keepnames class kotlinx.serialization.internal.*SerializersKt
# Keep enums used with serialization
-keepclassmembers enum * {
    @kotlin.jvm.JvmStatic public static **[] values();
    @kotlin.jvm.JvmStatic public static ** valueOf(java.lang.String);
    @kotlinx.serialization.SerialName public static final kotlinx.serialization.descriptors.SerialDescriptor $cachedDescriptor;
}
# Keep classes with @SerialName annotation and their members
-keepnames class * { @kotlinx.serialization.SerialName *; }
# Keep fields annotated with @Transient
-keepclassmembers class * {
    @kotlinx.serialization.Transient <fields>;
}
# Keep all your data classes that are marked as @Serializable
-keepclassmembers class * {
    @kotlinx.serialization.Serializable <fields>;
    @kotlinx.serialization.Serializable <init>(...);
}

# --- Ktor Client (including for Kotlinx Serialization) ---
-dontwarn io.ktor.client.features.websocket.**
-dontwarn io.ktor.client.engine.cio.**
-dontwarn io.ktor.network.tls.**
-dontwarn io.ktor.utils.io.jvm.javaio.**
# Don't warn about JSE classes Ktor might try to access for debug detection
-dontwarn java.lang.management.ManagementFactory
-dontwarn java.lang.management.RuntimeMXBean
# If you use specific Ktor engines and face issues, you might need to add rules for them.
# Ktor uses kotlinx.serialization, rules for that are above.
# Ensure your data classes used in Ktor requests/responses are @Serializable
# and covered by the serialization rules.

# --- Room ---
# Generally, Room works well with R8 and KSP.
# If you use custom TypeConverters or have complex scenarios,
# you might need to add specific -keep rules for them.
# For now, relying on AGP and Room's KSP integration.

# --- Glide ---
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$ImageType {
    **[] $VALUES;
    public *;
}
# For LibraryGlideModules, if you have any in your app or dependencies
-keep public class * implements com.bumptech.glide.module.LibraryGlideModule {
    public <init>(...);
}

# --- Kotlin Coroutines ---
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.coroutines.flow.internal.AbstractSharedFlowKt {
    java.lang.Object[] getBuffer();
}
# Keep Job and Deferred implementations
-keep class kotlinx.coroutines.JobSupport { <fields>; } # AtomicFU related
-keepclassmembernames class kotlinx.coroutines.internal.DispatchedContinuation {volatile java.lang.Object _reusableCancellableContinuation;}


# --- Jetpack Compose ---
# Compose is generally R8 friendly.
# The Android Gradle Plugin and Compose compiler handle most R8 requirements.
# If you reflect on Composable functions or internal Compose state, you might need specific rules.

# --- General good practices ---
# Keep custom attributes in XML being referred to from code
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses

# Keep default constructors for classes annotated with @Keep (useful for some libraries)
-keepclassmembers class ** { @androidx.annotation.Keep <init>(...); }
-keepclasseswithmembers class * { @androidx.annotation.Keep <fields>; }
-keepclasseswithmembers class * { @androidx.annotation.Keep <methods>; }

# Keep your application class
# -keep class com.tincek46.littlelemon.YourApplicationClass { <init>(); }
# (Uncomment and replace if you have a custom Application class)

# If you use any other libraries that rely on reflection (e.g., some DI frameworks, advanced serialization),
# make sure to add their ProGuard rules as well.
