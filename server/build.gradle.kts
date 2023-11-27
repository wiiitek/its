import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm")
    kotlin("plugin.spring") version "1.9.21"
    kotlin("plugin.jpa") version "1.9.21"
    id("groovy")

    id("org.springframework.cloud.contract") version "4.0.4"
    id("org.owasp.dependencycheck") version "9.0.1"
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

dependencyManagement {
    dependencies {
        // https://www.cve.org/CVERecord?id=CVE-2023-34062
        // https://github.com/advisories/GHSA-xjhv-p3fv-x24r
        // older reactor-netty-http is vulnerable:  CVE-2023-34062
        dependency("io.projectreactor.netty:reactor-netty-http:1.1.13")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.flywaydb:flyway-core")

    implementation("org.jetbrains.exposed:exposed-core:0.44.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.44.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.44.1")
    implementation("org.jetbrains.exposed:exposed-java-time:0.44.1")

    // https://www.programmersought.com/article/30275596545/
    runtimeOnly("com.h2database:h2:2.2.224")
    // may be surprising, but we use postgres only for integration testing
    testRuntimeOnly("org.postgresql:postgresql")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("org.spockframework:spock-core:2.4-M1-groovy-4.0")
    testImplementation("org.spockframework:spock-spring:2.4-M1-groovy-4.0")
    testImplementation("org.apache.groovy:groovy-all:4.0.15")

    // wiremock
    testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock:4.0.4")
    // spring cloud contract
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier:4.0.4")

    // testcontainers
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:spock")
    // https://stackoverflow.com/q/48956743
    testImplementation("io.zonky.test:embedded-database-spring-test:2.4.0")
    testImplementation("com.opentable.components:otj-pg-embedded:1.0.2")
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:1.19.3")
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
