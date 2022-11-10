import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.spring") version "1.7.21"
    kotlin("plugin.jpa") version "1.7.20"
    id("groovy")
    id("org.springframework.cloud.contract") version "3.1.4"
}

group = "pl.kubiczak.test.spring.integration"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

defaultTasks(":clean", ":build")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.flywaydb:flyway-core")

    implementation("io.springfox:springfox-boot-starter:3.0.0")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.spockframework:spock-core:2.3-groovy-3.0")
    testImplementation("org.spockframework:spock-spring:2.3-groovy-3.0")

    testImplementation("org.codehaus.groovy:groovy-all:3.0.13")
    testImplementation("org.codehaus.groovy.modules.http-builder:http-builder:0.7.1")

    // wiremock
    testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock:3.1.4")
    // spring cloud contract
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier:3.1.4")

    // testcontainers
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:spock")
    // https://www.programmersought.com/article/30275596545/
    testRuntimeOnly("com.h2database:h2")
    // https://stackoverflow.com/q/48956743
    testImplementation("io.zonky.test:embedded-database-spring-test:2.1.2")
    testImplementation("com.opentable.components:otj-pg-embedded:1.0.1")
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:1.17.5")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
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

contracts {
    setTestFramework("SPOCK")
    // configures package for generated test classes
    setBasePackageForTests("pl.kubiczak.test.spring.integration.demo.contracts")
    // configures base class for Spring boot contract tests
    baseClassMappings {
        baseClassMapping(".*", "pl.kubiczak.test.spring.integration.demo.MockMvcSpringBaseTest")
    }
}
