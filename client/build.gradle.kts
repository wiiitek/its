import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm")
    kotlin("plugin.spring") version "1.9.20"
    id("groovy")

    id("org.owasp.dependencycheck") version "8.4.2"
}

val javaVersion = JavaVersion.VERSION_17

group = "pl.kubiczak.test.spring.integration.demo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = javaVersion
java.targetCompatibility = javaVersion

// http://jeremylong.github.io/DependencyCheck/dependency-check-gradle/configuration.html
dependencyCheck {
    formats = listOf("HTML", "JUNIT")
    failBuildOnCVSS = 3.14f
    suppressionFile = "owasp-dependency-suppression.xml"
    // disable .NET assembly scanning
    analyzers.assemblyEnabled = false
}

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

dependencyManagement {
    dependencies {
        // for CVE-2023-3635
        // https://github.com/advisories/GHSA-w33c-445m-f8w7
        dependency("com.squareup.okio:okio:3.6.0")
        dependency("com.squareup.okio:okio-metadata:3.6.0")
        // next major version enforced because of
        // https://www.cve.org/CVERecord?id=CVE-2022-1471
        // https://github.com/advisories/GHSA-mjmj-j48q-9wg2
        dependency("org.yaml:snakeyaml:2.2")
    }
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
