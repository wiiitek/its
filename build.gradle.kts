plugins {
    kotlin("jvm") version "2.1.20" apply false

    id("org.owasp.dependencycheck") version "12.1.1"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

extra["vSpringOpenapi"] = "2.8.6"
extra["vSpringContract"] = "4.2.1"
extra["vSpringContractStubRunner"] = "4.2.1"
extra["vH2db"] = "2.3.232"
extra["vExposed"] = "0.61.0"
extra["vRetrofit"] = "2.11.0"
extra["vGroovy"] = "4.0.26"
extra["vSpock"] = "2.4-M6-groovy-4.0"
extra["vZonky"] = "2.6.0"
extra["vZonkyPostgres"] = "2.1.0"
extra["vTestContainers"] = "1.21.0"

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
    data {
        directory = "${rootProject.projectDir.absolutePath}/.gradle/dependency-check-data/9.0"
    }
}

defaultTasks("clean", "build")
