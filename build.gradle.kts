plugins {
    kotlin("jvm") version "2.0.21" apply false

    id("org.owasp.dependencycheck") version "11.1.0"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

extra["vSpringContract"] = "4.1.4"
extra["vSpringContractStubRunner"] = "4.1.3"
extra["vExposed"] = "0.55.0"
extra["vExposed"] = "0.55.0"
extra["vRetrofit"] = "2.11.0"
extra["vGroovy"] = "4.0.23"
extra["vSpock"] = "2.4-M4-groovy-4.0"

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
