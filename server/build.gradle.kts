import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm")
    kotlin("plugin.spring") version "2.1.0"
    kotlin("plugin.jpa") version "2.1.0"
    id("groovy")

    id("org.springframework.cloud.contract") version "4.1.4"
}

val javaVersion = JavaVersion.VERSION_17

group = "pl.kubiczak.test.spring.integration.demo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = javaVersion
java.targetCompatibility = javaVersion

val vSpringOpenapi: String by rootProject.extra
val vSpringContract: String by rootProject.extra
val vSpringContractStubRunner: String by rootProject.extra
val vH2db: String by rootProject.extra
val vExposed: String by rootProject.extra
val vGroovy: String by rootProject.extra
val vSpock: String by rootProject.extra
val vZonky: String by rootProject.extra
val vZonkyPostgres: String by rootProject.extra
val vTestContainers: String by rootProject.extra

dependencyManagement {
    imports {
        mavenBom("com.fasterxml.jackson:jackson-bom:2.18.2")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$vSpringOpenapi")

    implementation("org.jetbrains.exposed:exposed-core:$vExposed")
    implementation("org.jetbrains.exposed:exposed-dao:$vExposed")
    implementation("org.jetbrains.exposed:exposed-jdbc:$vExposed")
    implementation("org.jetbrains.exposed:exposed-java-time:$vExposed")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    runtimeOnly("org.flywaydb:flyway-core")
    runtimeOnly("org.flywaydb:flyway-database-postgresql")
    // https://www.programmersought.com/article/30275596545/
    runtimeOnly("com.h2database:h2:$vH2db")
    // may be surprising, but we use postgres only for integration testing
    testRuntimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("org.spockframework:spock-core:$vSpock")
    testImplementation("org.spockframework:spock-spring:$vSpock")
    testImplementation("org.apache.groovy:groovy-all:$vGroovy")

    // wiremock
    testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock:$vSpringContract")
    // spring cloud contract
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier:$vSpringContract")

    // testcontainers
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:spock")
    // https://stackoverflow.com/q/48956743
    testImplementation("io.zonky.test:embedded-database-spring-test:$vZonky")
    testImplementation("io.zonky.test:embedded-postgres:$vZonkyPostgres")
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:$vTestContainers")
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
    setBasePackageForTests("pl.kubiczak.test.spring.integration.demo.server.contracts")
    // configures base class for Spring boot contract tests
    baseClassMappings {
        baseClassMapping(".*", "pl.kubiczak.test.spring.integration.demo.server.MockMvcSpringBaseTest")
    }
}

// for consuming stubs locally https://stackoverflow.com/a/62077808/1823545
val stubs: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = false
}

artifacts {
    add("stubs", tasks.verifierStubsJar.get())
}
