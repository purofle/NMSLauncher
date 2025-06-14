import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "2.1.21"
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

group = "com.github.purofle"
version = "1.0"

repositories {
    mavenCentral()
    google()
}

buildscript {
    repositories {
        mavenCentral()
    }
     dependencies {
         classpath(libs.proguard.plugin)
     }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.log4j.api)
    implementation(libs.log4j.core)

    testImplementation(kotlin("test"))

    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)

    implementation(project(":NMSLCore"))
}

tasks.test {
    useJUnit()
}

compose.desktop {
    application {
        mainClass = "com.github.purofle.nmsl.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "NMSLauncher"
            packageVersion = "1.0.0"

            copyright = "Copyright (C) Purofle 2023-2025"
        }
        buildTypes.release.proguard {
            configurationFiles.from(project.file("compose-desktop.pro"))
        }
    }
}
