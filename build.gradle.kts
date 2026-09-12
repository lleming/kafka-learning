plugins {
    val springBootVersion = "2.6.15" // Last stable patch for the 2.6.x line

    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    java
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11 // Spring Boot 2.6 officially supports Java 8, 11, and 17

repositories {
    mavenCentral()
}

dependencies {
    // Web MVC
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Email Message Support
    implementation("org.springframework.boot:spring-boot-starter-mail")

    // Apache Kafka
    implementation("org.springframework.kafka:spring-kafka")

    // Lombok (Annotation Processor & Dependency)
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
