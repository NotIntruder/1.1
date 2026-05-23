plugins {
    kotlin("jvm") version "2.1.21"
    application                          // add this
}

group = "org.notintruder"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(23)
}

application {
    mainClass.set("cmd.tcplistener.MainKt")  // your main class
}

tasks.withType<JavaExec> {
    standardInput = System.`in`              // keeps stdin open
    jvmArgs("-Djava.io.stdout.encoding=UTF-8")
}

tasks.test {
    useJUnitPlatform()
}