plugins {
    kotlin("jvm") version "2.0.21" apply false

    id("org.owasp.dependencycheck") version "10.0.4"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

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
