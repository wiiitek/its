plugins {
    kotlin("jvm") version "1.9.22" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}


defaultTasks("clean", "build")
