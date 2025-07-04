plugins {
    kotlin("jvm") version "2.0.0"
//    kotlin("plugin.serialization") version "2.1.10"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    application
}

group = "ec.solmedia"
version = "0.1.0"

val mcpVersion = "0.5.0"
val jvmMainClass = "ec.solmedia.McpKt"

application {
    mainClass.set(jvmMainClass)
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.modelcontextprotocol:kotlin-sdk:$mcpVersion")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
tasks.jar {
    manifest {
        attributes["Main-Class"] = jvmMainClass
    }
}
tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    manifest {
        attributes["Main-Class"] = jvmMainClass
    }
    archiveBaseName.set("kotlin-mcp")
    archiveClassifier.set("")
    archiveVersion.set("0.1.0")
}
