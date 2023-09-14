plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.allopen") version "1.9.0"
    kotlin("plugin.spring") version "1.9.0"
    kotlin("plugin.noarg") version "1.9.0"
    id("io.gitlab.arturbosch.detekt") version "1.23.1"
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive:3.1.2")
    implementation("org.springframework.boot:spring-boot-starter-security:3.1.2")
    implementation("org.springframework.boot:spring-boot-starter-web:3.1.2")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.1.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
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
