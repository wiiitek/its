import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.springframework.boot") version "3.5.4"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm")
    kotlin("plugin.spring") version "2.2.10"
    id("groovy")
}

val javaVersion = JavaVersion.VERSION_17

group = "pl.kubiczak.test.spring.integration.demo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = javaVersion
java.targetCompatibility = javaVersion

val vRetrofit: String by rootProject.extra
val vGroovy: String by rootProject.extra
val vSpock: String by rootProject.extra
val vSpringContractStubRunner: String by rootProject.extra

dependencyManagement {
    dependencies {
        // newer versions to fix CVE-2025-22233
        // https://spring.io/security/cve-2025-22233
        dependency("org.springframework:spring-context:6.2.9")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")

    implementation("com.squareup.retrofit2:retrofit:$vRetrofit")
    implementation("com.squareup.retrofit2:converter-gson:$vRetrofit")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("org.spockframework:spock-core:$vSpock")
    testImplementation("org.spockframework:spock-spring:$vSpock")
    testImplementation("org.apache.groovy:groovy-all:$vGroovy")

    testImplementation(project(":server", "stubs"))

    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner:$vSpringContractStubRunner")
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        freeCompilerArgs.addAll(listOf("-Xjsr305=strict"))
        jvmTarget.set(JvmTarget.fromTarget(javaVersion.majorVersion))
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
