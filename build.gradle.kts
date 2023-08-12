import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.serialization)
}

group = "com.github.purofle"
version = "1.0"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
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

    implementation(project(":library"))
    implementation(project(":NMSLCore"))
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

compose.desktop {
	application {
		buildTypes.release.proguard {
			configurationFiles.from(project.file("compose-desktop.pro"))
		}
	}
}
