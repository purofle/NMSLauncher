plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlin.serialization)
}

group = "com.github.purofle"
version = "1.0"

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.log4j.api)
    implementation(libs.log4j.core)
    implementation(libs.slf4j.api)
    implementation(libs.slf4j.log4j2)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.client.negotiation)
    implementation(libs.ktor.client.auth)


    testImplementation(kotlin("test"))
}
