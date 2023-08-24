plugins {
    `java-library`
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.allopen") version "1.9.10"
    kotlin("plugin.spring") version "1.9.10"
    kotlin("plugin.noarg") version "1.9.10"
    kotlin("plugin.jpa") version "1.9.10"
    id("io.gitlab.arturbosch.detekt") version "1.23.1"
}

repositories {
    mavenCentral()
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa:3.1.2")
    api("org.springframework.boot:spring-boot-starter-security:3.1.2")
    api("org.springframework.boot:spring-boot-starter-web:3.1.2")
    api("io.jsonwebtoken:jjwt:0.9.1")
    api("org.springframework.boot:spring-boot-starter-validation:3.1.2")
    api("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
    api("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    api("javax.xml.bind:jaxb-api:2.3.1")
    runtimeOnly("org.springframework.boot:spring-boot-devtools:3.1.2")
    runtimeOnly("com.mysql:mysql-connector-j:8.0.33")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.2")
    testImplementation("org.springframework.security:spring-security-test:6.1.2")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "ajax-onboarding-project"
java.sourceCompatibility = JavaVersion.VERSION_17

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

kotlin {
    jvmToolchain(17)
}
