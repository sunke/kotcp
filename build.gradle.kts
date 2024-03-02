plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "net.codenest"
version = "1.0-SNAPSHOT"

val lombok_version = "1.18.30"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:$lombok_version")
    annotationProcessor("org.projectlombok:lombok:$lombok_version")

    testCompileOnly("org.projectlombok:lombok:$lombok_version")
    testAnnotationProcessor("org.projectlombok:lombok:$lombok_version")

    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.10")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.2")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(20)
}

application {
    mainClass.set("MainKt")
}