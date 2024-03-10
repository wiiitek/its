import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm")
    kotlin("plugin.spring") version "1.9.23"
    id("groovy")

    id("org.owasp.dependencycheck") version "9.0.9"
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
    nvd.apiKey = System.getenv("NVD_API_KEY")
    // https://github.com/jeremylong/DependencyCheck/issues/6107#issuecomment-1824010802
    nvd.delay = 16000
}

val vLogback = "1.5.2"
val vRetrofit = "2.9.0"
val vSpock = "2.4-M2-groovy-4.0"
val vOkio = "3.7.0"

dependencyManagement {
    dependencies {
        // https://nvd.nist.gov/vuln/detail/CVE-2023-6378
        // https://github.com/advisories/GHSA-vmq6-5m68-f53m
        // default logback-classic from Spring is vulnerable: CVE-2023-6378
        dependency("ch.qos.logback:logback-classic:$vLogback")
        dependency("ch.qos.logback:logback-core:$vLogback")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")

    implementation("com.squareup.retrofit2:retrofit:$vRetrofit")
    implementation("com.squareup.retrofit2:converter-gson:$vRetrofit")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("org.spockframework:spock-core:$vSpock")
    testImplementation("org.spockframework:spock-spring:$vSpock")
    testImplementation("org.apache.groovy:groovy-all:4.0.19")

    testImplementation(project(":server", "stubs"))

    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner:4.1.1")
}

dependencyManagement {
    dependencies {
        // for CVE-2023-3635
        // https://github.com/advisories/GHSA-w33c-445m-f8w7
        dependency("com.squareup.okio:okio:$vOkio")
        dependency("com.squareup.okio:okio-metadata:$vOkio")
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
