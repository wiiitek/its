import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm")
    kotlin("plugin.spring") version "1.9.10"
    id("groovy")
}

val javaVersion = JavaVersion.VERSION_17

group = "pl.kubiczak.test.spring.integration.demo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = javaVersion
java.targetCompatibility = javaVersion

repositories {
    mavenCentral()
}

defaultTasks(":clean", ":build")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("org.spockframework:spock-core:2.4-M1-groovy-4.0")
    testImplementation("org.spockframework:spock-spring:2.4-M1-groovy-4.0")
    testImplementation("org.apache.groovy:groovy-all:4.0.15")

    testImplementation(project(":server", "stubs"))

    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner:4.0.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = javaVersion.toString()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
