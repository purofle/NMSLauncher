import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.30"
    id("org.jetbrains.compose") version "0.4.0"
    kotlin("plugin.serialization") version "1.4.30"
}

group = "com.github.purofle"
version = "1.0"

repositories {
    jcenter()
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}
val experimentalAnnotations = arrayOf(
    "kotlin.RequiresOptIn"
)


dependencies {
    val ktorversion = "1.6.0"
    val logbackversion = "1.2.3"
    testImplementation(kotlin("test"))
    implementation(compose.desktop.currentOs)
    implementation("io.ktor:ktor-client-core:$ktorversion")
    implementation("io.ktor:ktor-client-cio:$ktorversion")
    implementation("io.ktor:ktor-client-serialization:$ktorversion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
    implementation("ch.qos.logback:logback-classic:$logbackversion")
    implementation("io.ktor:ktor-client-logging:$ktorversion")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=org.mylibrary.OptInAnnotation"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "NMSL-Launcher"
            packageVersion = "1.0.0"
        }
    }
}