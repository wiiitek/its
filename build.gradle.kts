plugins {
    kotlin("jvm") version "1.9.10" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}


defaultTasks("clean", "build")
