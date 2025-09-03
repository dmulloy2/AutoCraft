import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`
    `maven-publish`
    id("com.gradleup.shadow") version "9.0.2"
}

group = "net.dmulloy2"
version = "3.3.0-SNAPSHOT"
description = "AutoCraft"

var isSnapshot = version.toString().endsWith("-SNAPSHOT")
var useJitPack = project.findProperty("USE_JITPACK") != null

repositories {
    mavenLocal()
    mavenCentral()

    if (useJitPack) {
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    if (useJitPack) {
        implementation("com.github.dmulloy2:SwornAPI:master-SNAPSHOT")
    } else {
        implementation("net.dmulloy2:swornapi:2.0.0-SNAPSHOT")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks {
    named<ShadowJar>("shadowJar"){
        dependencies {
            include(dependency("net.dmulloy2:swornapi:.*"))
        }
        relocate("net.dmulloy2.swornapi", "net.dmulloy2.autocraft.swornapi")
        archiveFileName.set("AutoCraft.jar")
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    test {
        useJUnitPlatform()
    }

    processResources {
        var buildNumber = System.getenv("BUILD_NUMBER")
        var fullVersion = if (isSnapshot && buildNumber != null)
            "$version-$buildNumber"
        else
            version.toString()

        eachFile { expand("version" to fullVersion) }
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}
