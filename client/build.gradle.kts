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
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("org.spockframework:spock-core:2.4-M1-groovy-4.0")
    testImplementation("org.spockframework:spock-spring:2.4-M1-groovy-4.0")
    testImplementation("org.apache.groovy:groovy-all:4.0.15")

//    testImplementation("pl.kubiczak.test.spring.integration:server:0.0.1-SNAPSHOT")
    testImplementation(project(":server", "stubs"))

    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner:3.1.0")

    // Additional dependency for JUnit Platform
    testImplementation("org.junit.platform:junit-platform-console:1.8.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = javaVersion.toString()
    }
}

// https://stackoverflow.com/a/50719594/1823545
tasks.withType<BootRun> {
    val activeProfiles = System.getProperty("spring.profiles.active")
    this.systemProperty("spring.profiles.active", activeProfiles)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
