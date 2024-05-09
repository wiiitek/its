import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("jvm")
    kotlin("plugin.spring") version "1.9.24"
    kotlin("plugin.jpa") version "1.9.24"
    id("groovy")

    id("org.springframework.cloud.contract") version "4.1.2"
    id("org.owasp.dependencycheck") version "9.1.0"
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

val vExposed = "0.50.0"
val vSpock = "2.4-M4-groovy-4.0"
val vSpringContract = "4.1.2"

dependencyManagement {
    imports {
        mavenBom("com.fasterxml.jackson:jackson-bom:2.17.1")
    }
    dependencies {
        // newer versions to fix CVE-2024-29025
        // https://nvd.nist.gov/vuln/detail/CVE-2024-29025
        dependency("io.projectreactor.netty:reactor-netty-http:1.1.18")
        dependency("io.netty:netty-codec-http:4.1.109.Final")
        // https://spring.io/security/cve-2024-22262
        dependency("org.springframework:spring-web:6.1.6")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.flywaydb:flyway-core")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    implementation("org.jetbrains.exposed:exposed-core:$vExposed")
    implementation("org.jetbrains.exposed:exposed-dao:$vExposed")
    implementation("org.jetbrains.exposed:exposed-jdbc:$vExposed")
    implementation("org.jetbrains.exposed:exposed-java-time:$vExposed")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // https://www.programmersought.com/article/30275596545/
    runtimeOnly("com.h2database:h2:2.2.224")
    // may be surprising, but we use postgres only for integration testing
    testRuntimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("org.spockframework:spock-core:$vSpock")
    testImplementation("org.spockframework:spock-spring:$vSpock")
    testImplementation("org.apache.groovy:groovy-all:4.0.21")

    // wiremock
    testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock:$vSpringContract")
    // spring cloud contract
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier:$vSpringContract")

    // testcontainers
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:spock")
    // https://stackoverflow.com/q/48956743
    testImplementation("io.zonky.test:embedded-database-spring-test:2.5.1")
    testImplementation("com.opentable.components:otj-pg-embedded:1.0.3")
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:1.19.8")
    }
    dependencies {
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
