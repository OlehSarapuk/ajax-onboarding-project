dependencies {
    implementation(project(":common-subdomain"))
    implementation(project(":user-subdomain"))
    implementation("org.springframework.boot:spring-boot-starter-security:3.1.2")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
}
