pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }
}
rootProject.name = "NMSLauncher"
include("NMSLCore")
