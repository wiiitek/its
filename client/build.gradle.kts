plugins {
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm")
    kotlin("plugin.spring") version "1.9.10"
    id("groovy")
}

val javaVersion = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

defaultTasks(":clean", ":build")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("org.spockframework:spock-core:2.4-M1-groovy-4.0")
    testImplementation("org.spockframework:spock-spring:2.4-M1-groovy-4.0")
    testImplementation("org.apache.groovy:groovy-all:4.0.15")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
