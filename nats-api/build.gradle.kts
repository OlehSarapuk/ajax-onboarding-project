plugins {
    idea
    kotlin("jvm") version "1.9.0"
    id("com.google.protobuf") version "0.9.4"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.protobuf:protobuf-java:3.24.2")
    implementation("com.google.protobuf:protobuf-java-util:3.20.1")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "nats-api"

kotlin {
    jvmToolchain(17)
}

object GeneratedFileDir {
    const val java = "/main/java"
    const val base = "/src/generated"
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.20.1"
    }

    generateProtoTasks {
        all().configureEach {
            generateDescriptorSet = true
            descriptorSetOptions.includeImports = true
        }
    }
    generatedFilesBaseDir = "$projectDir${GeneratedFileDir.base}"
}

idea {
    module {
        sourceDirs = sourceDirs + file("${protobuf.generatedFilesBaseDir}${GeneratedFileDir.java}")
    }
}

tasks {
    clean {
        delete(protobuf.generatedFilesBaseDir)
    }
}
