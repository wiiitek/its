import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.8.10"
    kotlin("plugin.spring") version "1.8.10"
    kotlin("plugin.jpa") version "1.8.10"
    id("groovy")
    id("org.springframework.cloud.contract") version "4.0.2"
}

val javaVersion = JavaVersion.VERSION_17

group = "pl.kubiczak.test.spring.integration"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = javaVersion

repositories {
    mavenCentral()
}

defaultTasks(":clean", ":build")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.flywaydb:flyway-core")
    // https://www.programmersought.com/article/30275596545/
    runtimeOnly("com.h2database:h2")
    // may be surprising, but we use postgres only for integration testing
    testRuntimeOnly("org.postgresql:postgresql")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.4")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("org.spockframework:spock-core:2.4-M1-groovy-4.0")
    testImplementation("org.spockframework:spock-spring:2.4-M1-groovy-4.0")
    testImplementation("org.apache.groovy:groovy-all:4.0.10")

    // wiremock
    testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock:4.0.2")
    // spring cloud contract
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier:4.0.2")

    // testcontainers
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:spock")
    // https://stackoverflow.com/q/48956743
    testImplementation("io.zonky.test:embedded-database-spring-test:2.2.0")
    testImplementation("com.opentable.components:otj-pg-embedded:1.0.1")
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:1.17.6")
    }
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

contracts {
    setTestFramework("SPOCK")
    // configures package for generated test classes
    setBasePackageForTests("pl.kubiczak.test.spring.integration.demo.contracts")
    // configures base class for Spring boot contract tests
    baseClassMappings {
        baseClassMapping(".*", "pl.kubiczak.test.spring.integration.demo.MockMvcSpringBaseTest")
    }
}
