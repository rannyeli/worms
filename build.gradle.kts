plugins {
    java
    application
    id("org.beryx.runtime") version "1.13.1"
}

group = "com.worms"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jgrapht:jgrapht-core:1.5.2")
}

application {
    mainClass.set("com.worms.GameFrame")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.worms.GameFrame"
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

runtime {
    options.set(listOf("--strip-debug", "--no-header-files", "--no-man-pages"))
    launcher {
        noConsole = true
    }
    jpackage {
        val os = org.gradle.internal.os.OperatingSystem.current()
        installerType = when {
            os.isMacOsX -> "dmg"
            os.isWindows -> "msi"
            else -> "deb"
        }
        imageName = "Worms"
        installerName = "Worms"
        appVersion = "1.0.0"
    }
}
