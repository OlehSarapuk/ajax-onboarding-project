dependencies {
    implementation(project(":nats-api"))
    implementation(project(":common-subdomain"))
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive:3.1.2")
    testImplementation(project(":"))
}
