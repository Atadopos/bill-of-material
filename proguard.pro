# ProGuard configuration for JavaFX, Kotlin, Jakarta Persistence, and other libraries

# Kotlin runtime
-keep class kotlin.** { *; }
-keep class kotlinx.** { *; }
#-keep class kotlin.reflect.** { *; }
#-adaptresourcefilenames META-INF/*.kotlin_module


# JavaFX specific rules
-keep class javafx.** { *; }
-keep interface javafx.** { *; }
-keep class com.sun.javafx.** { *; }
-keep class com.sun.prism.** { *; }

# Jakarta Persistence (previously JPA)
-keep class jakarta.persistence.** { *; }

# Apache POI library
-keep class org.apache.poi.** { *; }

# Specific library configurations
-keep class atlantafx.base.** { *; }

# Lombok
-keepattributes *Annotation*
-keep class lombok.** { *; }
-dontwarn lombok.**
-keep @lombok.* class * { *; }

# EclipseLink
-keep class org.eclipse.persistence.** { *; }

# Reflection and annotations
-keepattributes *Annotation*, InnerClasses, Signature
-keepclassmembers class * {
    @java.beans.ConstructorProperties <init>(...);
}

# Kotlin specifics
-keep class kotlin.Metadata { *; }
#-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
-keep class **$Companion { *; }
-keepclassmembers class * {
    @kotlin.jvm.JvmStatic *;
}

# Your module specifics
-keep class link.pihda.billofmaterial.** { *; }
-keepclassmembers class link.pihda.billofmaterial.** { *; }

# Prevent removal of application entry points
-keepclasseswithmembers public class * {
    public static void main(java.lang.String[]);
}

# Additional rules if you're using FXML
-keepclasseswithmembers class * {
    @javafx.fxml.FXML *;
}

-verbose
-keep class javafx.scene.control.** { *; }
-keep class javafx.scene.control.** { *; }
