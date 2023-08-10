plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlin.serialization)
}

group = "com.github.purofle"
version = "1.0"

repositories {
    mavenCentral()
}

val ktorVersion: String by project

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.google.code.gson:gson:2.9.0")

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.log4j.core)
    implementation(libs.log4j.api)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.logging)

    testImplementation(kotlin("test"))
}
