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

    implementation("org.apache.logging.log4j:log4j-api:2.18.0")
    implementation("org.apache.logging.log4j:log4j-core:2.18.0")
    implementation("io.ktor:ktor-client-core-jvm:2.1.0")
    implementation("io.ktor:ktor-client-cio-jvm:2.1.0")
    implementation("io.ktor:ktor-client-logging-jvm:2.1.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
