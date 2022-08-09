import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.0"
    kotlin("kapt") version "1.7.0"
    id("org.jetbrains.compose") version "1.2.0-alpha01-dev741"
    kotlin("plugin.serialization") version "1.7.0"
}

group = "com.github.purofle"
version = "1.0"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    google()
}

@OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-parcelize-compiler:1.5.21")
    val decomposeVersion = "0.6.0"
    testImplementation(kotlin("test"))
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(project(":library"))
    implementation(project(":NMSLCore"))
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("com.arkivanov.decompose:decompose:$decomposeVersion")
    implementation("com.arkivanov.decompose:extensions-compose-jetbrains:$decomposeVersion")
    implementation("com.google.dagger:dagger:2.42")
    kapt("com.google.dagger:dagger-compiler:2.42")
    implementation("org.junit.jupiter:junit-jupiter:5.8.2")
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.guardsquare:proguard-gradle:7.2.0")
    }
}

tasks.register<proguard.gradle.ProGuardTask>("obfuscate") {
    val packageUberJarForCurrentOS by tasks.getting
    dependsOn(packageUberJarForCurrentOS)
    val files = packageUberJarForCurrentOS.outputs.files
    injars(files)
    outjars(files.map { file -> File(file.parentFile, "${file.nameWithoutExtension}.min.jar") })

    val library = if (System.getProperty("java.version").startsWith("1.")) "lib/rt.jar" else "jmods"
    libraryjars("${System.getProperty("java.home")}/$library")

    configuration("proguard-rules.pro")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}


compose.desktop {
    application {
        mainClass = "com.github.purofle.nmsl.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "NMSLauncher"
            packageVersion = "1.0.0"
        }
    }
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}