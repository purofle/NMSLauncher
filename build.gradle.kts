import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("kapt") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.0-alpha1-dev550"
    kotlin("plugin.serialization") version "1.6.10"
}

group = "com.github.purofle"
version = "1.0"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    google()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-parcelize-compiler:1.5.21")
    val ktorVersion = "1.6.7"
    val logbackVersion = "1.2.10"
    val decomposeVersion = "0.4.0"
    testImplementation(kotlin("test"))
    implementation(compose.desktop.currentOs)
    implementation(project(":library"))
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    implementation("com.arkivanov.decompose:decompose:$decomposeVersion")
    implementation("com.arkivanov.decompose:extensions-compose-jetbrains:$decomposeVersion")
    implementation("com.google.dagger:dagger:2.40.5")
    kapt("com.google.dagger:dagger-compiler:2.40.5")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

compose.desktop {
    application {
        mainClass = "com.github.purofle.nmsl.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "NMSL-Launcher"
            packageVersion = "1.0.0"
        }
    }
}
