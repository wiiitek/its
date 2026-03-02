plugins {
    kotlin("jvm") version "2.2.21" apply false

    id("org.owasp.dependencycheck") version "12.1.9"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

extra["vSpringOpenapi"] = "2.8.14"
extra["vSpringContract"] = "5.0.0"
extra["vSpringContractStubRunner"] = "4.3.0"
extra["vH2db"] = "2.4.240"
extra["vExposed"] = "0.61.0"
extra["vRetrofit"] = "3.0.0"
extra["vGroovy"] = "5.0.2"
extra["vSpock"] = "2.4-M6-groovy-4.0"
extra["vZonky"] = "2.7.1"
extra["vZonkyPostgres"] = "2.2.0"
extra["vTestContainers"] = "2.0.2"

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
