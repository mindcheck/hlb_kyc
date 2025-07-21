# Rules to keep your public API.
# Replace com.hlb.mylibrary.model with the package of your public API.
-keep public class com.hlb.mylibrary.model.** {
    public *;
}

# Rules to keep any public interfaces.
-keep interface com.hlb.mylibrary.interfaces.** {
    public *;
}

# If your library uses JNI (native code), keep those methods.
-keepclasseswithmembernames class * {
    native <methods>;
}

