plugins {
    kotlin("jvm") version "1.9.24" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}


defaultTasks("clean", "build")
