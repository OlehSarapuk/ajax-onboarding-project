dependencies {
    implementation(project(":nats-api"))
    implementation(project(":common-subdomain"))
    implementation(project(":movie-subdomain"))
    implementation(project(":cinema-hall-subdomain"))
    implementation(project(":movie-session-subdomain"))
    implementation("org.springframework.boot:spring-boot-starter-security:3.1.2")
    testImplementation(project(":"))
}
