plugins {
    java
    id("org.springframework.boot") version "3.0.3"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "com.veinsmoke"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17



configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()

}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("com.cloudinary:cloudinary-http44:1.32.0")
    implementation("com.cloudinary:cloudinary-core:1.32.0")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
    implementation("org.mapstruct:mapstruct:1.5.3.Final")
    implementation("org.mapstruct:mapstruct-processor:1.5.3.Final")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor ("org.mapstruct:mapstruct-processor:1.5.3.Final")
    annotationProcessor ("org.projectlombok:lombok-mapstruct-binding:0.2.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")


}

tasks.withType<Test> {
    useJUnitPlatform()
}
