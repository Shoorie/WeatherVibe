# Kotlinx Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep,includedescriptorclasses class com.weather.vibe.**$$serializer { *; }
-keepclassmembers class com.weather.vibe.** {
    *** Companion;
}
-keepclasseswithmembers class com.weather.vibe.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Ktor
-keepclassmembers class io.ktor.** { volatile <fields>; }
-keep class io.ktor.client.engine.okhttp.** { *; }
-dontwarn io.ktor.**

# OkHttp (Ktor engine)
-dontwarn okhttp3.internal.platform.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

# Koin
-keep class com.weather.vibe.**.di.** { *; }
-keepnames class com.weather.vibe.** { *; }
