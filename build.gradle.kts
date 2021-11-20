import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    id("org.jetbrains.compose") version "1.0.0-beta6-dev462"
    kotlin("plugin.serialization") version "1.5.31"
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
    val ktorVersion = "1.6.4"
    val logbackVersion = "1.2.7"
    testImplementation(kotlin("test"))
    implementation(compose.desktop.currentOs)
    implementation(project(":library"))
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
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
