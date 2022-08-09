plugins {
    kotlin("jvm")
}

group = "com.github.purofle"
version = "1.0"

repositories {
    mavenCentral()
}

val ktorVersion: String by project

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.google.code.gson:gson:2.9.0")

    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
