import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    alias(libs.plugins.compose)
}

group = "com.github.purofle"
version = "1.0"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    google()
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}


dependencies {
    implementation(kotlin("stdlib"))

    implementation(libs.log4j.api)
    implementation(libs.log4j.core)

    implementation("dev.kdrag0n:colorkt:1.0.5")
    implementation(compose.material3)
    implementation(compose.desktop.currentOs)
}