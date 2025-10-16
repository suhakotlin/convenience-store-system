import org.gradle.api.file.DuplicatesStrategy

plugins {
    kotlin("jvm") version "2.2.0"
    application
}

group = "com.bible"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)  
}

application {
    
    mainClass.set("MainKt")
}
tasks.jar {
    
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }

    
    from(
        configurations.runtimeClasspath.get().map { file ->
            if (file.isDirectory) file else zipTree(file)
        }
    )
}
