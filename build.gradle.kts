plugins {
    kotlin("jvm") version "1.9.21" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}


defaultTasks("clean", "build")
