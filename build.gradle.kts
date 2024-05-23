plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

sourceSets {
    main {
        java {
            srcDir("src")
        }
        resources {
            srcDir("res")
        }
    }
}

group = "gecko10000.texturedump"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://redempt.dev/")
    mavenLocal()
}

dependencies {
    compileOnly(kotlin("stdlib", version = "1.9.22"))
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")

    compileOnly("gecko10000.geckolib:GeckoLib:1.0-SNAPSHOT")
    implementation("gecko10000.geckoconfig:GeckoConfig:1.0-SNAPSHOT")

    compileOnly("com.github.Redempt:RedLib:6.5.10")

    implementation("io.insert-koin:koin-core:3.5.3") {
        exclude("org.jetbrains.kotlin")
    }
}

kotlin {
    jvmToolchain(17)
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}

// simple script to sync the plugin to my test server
tasks.register("update") {
    dependsOn(tasks.build)
    doLast {
        exec {
            workingDir(".")
            commandLine("../../dot/local/bin/update.sh")
        }
    }
}
