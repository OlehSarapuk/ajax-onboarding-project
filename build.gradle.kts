plugins {
    idea
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.allopen") version "1.9.0"
    kotlin("plugin.spring") version "1.9.0"
    kotlin("plugin.noarg") version "1.9.0"
    id("io.gitlab.arturbosch.detekt") version "1.23.1"
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
    id("com.google.protobuf") version "0.9.4"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb:3.1.2")
    implementation("org.springframework.boot:spring-boot-starter-security:3.1.2")
    implementation("org.springframework.boot:spring-boot-starter-web:3.1.2")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.1.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("io.nats:jnats:2.16.14")
    implementation("com.google.protobuf:protobuf-java:3.24.2")
    implementation("com.google.protobuf:protobuf-java-util:3.20.1")
    runtimeOnly("org.springframework.boot:spring-boot-devtools:3.1.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.2")
    testImplementation("org.springframework.security:spring-security-test:6.1.2")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "ajax-onboarding-project"

kotlin {
    jvmToolchain(17)
}

noArg {
    annotation("org.springframework.web.bind.annotation.RestController")
}

object GeneratedFileDir {
    const val base = "/src/generated"
    const val java = "/main/java"
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

tasks.withType<Test> {
    useJUnitPlatform()
}
