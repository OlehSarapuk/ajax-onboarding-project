plugins {
    idea
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.allopen") version "1.9.0"
    kotlin("plugin.spring") version "1.9.0"
    kotlin("plugin.noarg") version "1.9.0"
    id("io.gitlab.arturbosch.detekt") version "1.23.1"
    id("com.google.protobuf") version "0.9.4"
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":nats-api"))
    implementation(project(":movie-subdomain"))
    implementation(project(":cinema-hall-subdomain"))
    implementation(project(":movie-session-subdomain"))
    implementation(project(":authentication-subdomain"))
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive:3.1.2")
    implementation("org.springframework.boot:spring-boot-starter-security:3.1.2")
    implementation("org.springframework.boot:spring-boot-starter-webflux:3.1.2")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.1.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("io.nats:jnats:2.16.14")
    implementation("com.google.protobuf:protobuf-java:3.24.2")
    implementation("com.google.protobuf:protobuf-java-util:3.20.1")
    implementation("io.grpc:grpc-protobuf:1.58.0")
    implementation("io.grpc:grpc-netty:1.58.0")
    implementation("io.grpc:grpc-stub:1.58.0")
    implementation("com.salesforce.servicelibs:reactor-grpc:1.2.4")
    implementation("com.salesforce.servicelibs:reactive-grpc-common:1.2.4")
    implementation("com.salesforce.servicelibs:reactor-grpc-stub:1.2.4")
    implementation("org.springframework.kafka:spring-kafka:3.0.11")
    implementation("io.projectreactor.kafka:reactor-kafka:1.3.21")
    runtimeOnly("org.springframework.boot:spring-boot-devtools:3.1.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.2")
    testImplementation("io.projectreactor:reactor-test:3.5.10")
    testImplementation("com.willowtreeapps.assertk:assertk:0.27.0")
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

tasks.withType<Test> {
    useJUnitPlatform()
}

subprojects{
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.allopen")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.noarg")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive:3.1.2")
        implementation("org.springframework.boot:spring-boot-starter-webflux:3.1.2")
        implementation("org.springframework.boot:spring-boot-starter-validation:3.1.2")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
        implementation("javax.xml.bind:jaxb-api:2.3.1")
        implementation("io.nats:jnats:2.16.14")
        implementation("com.google.protobuf:protobuf-java:3.24.2")
        implementation("com.google.protobuf:protobuf-java-util:3.20.1")
        implementation("io.grpc:grpc-protobuf:1.58.0")
        implementation("io.grpc:grpc-netty:1.58.0")
        implementation("io.grpc:grpc-stub:1.58.0")
        implementation("com.salesforce.servicelibs:reactor-grpc:1.2.4")
        implementation("com.salesforce.servicelibs:reactive-grpc-common:1.2.4")
        implementation("com.salesforce.servicelibs:reactor-grpc-stub:1.2.4")
        implementation("org.springframework.kafka:spring-kafka:3.0.11")
        implementation("io.projectreactor.kafka:reactor-kafka:1.3.21")
        runtimeOnly("org.springframework.boot:spring-boot-devtools:3.1.2")
        testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.2")
        testImplementation("io.projectreactor:reactor-test:3.5.10")
        testImplementation("com.willowtreeapps.assertk:assertk:0.27.0")
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

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
