plugins {
    kotlin("jvm") version "1.9.20" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}


defaultTasks("clean", "build")
