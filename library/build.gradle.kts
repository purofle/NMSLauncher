import org.jetbrains.compose.compose

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

group = "com.github.purofle"
version = "1.0"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    google()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}

@OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
dependencies {
    implementation(kotlin("stdlib"))
    implementation("ch.qos.logback:logback-classic:1.2.7")
    implementation("dev.kdrag0n:colorkt:1.0.5")
    implementation(compose.material3)
    implementation(compose.desktop.currentOs)
}