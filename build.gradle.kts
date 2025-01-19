import gg.essential.gradle.multiversion.StripReferencesTransform.Companion.registerStripReferencesAttribute
import gg.essential.gradle.util.*
import gg.essential.gradle.util.RelocationTransform.Companion.registerRelocationAttribute
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.23"
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.8.0"
    id("org.jetbrains.dokka") version "1.9.20"
    id("gg.essential.defaults")
    id("gg.essential.defaults.maven-publish")
}

group = "gg.essential"
version = "DIAMOND-10"

kotlin.jvmToolchain {
    (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(8))
}

tasks.withType<KotlinCompile> {
    setJvmDefault("all-compatibility")
    kotlinOptions {
        languageVersion = "1.6"
        apiVersion = "1.6"
    }
}

val common = registerStripReferencesAttribute("common") {
    excludes.add("net.minecraft")
}

dependencies {
    compileOnly(libs.kotlin.stdlib.jdk8)
    compileOnly(libs.kotlin.reflect)
    compileOnly(libs.jetbrains.annotations)

    compileOnly(project(":mc-stubs"))
    // Depending on LWJGL3 instead of 2 so we can choose opengl bindings only
    compileOnly("org.lwjgl:lwjgl-opengl:3.3.1")
    // Depending on 1.8.9 for all of these because that's the oldest version we support
    compileOnly(libs.universalcraft.forge10809) {
        attributes { attribute(common, true) }
    }
    compileOnly("com.google.code.gson:gson:2.2.4")
}

tasks.processResources {
    inputs.property("project.version", project.version)
    filesMatching("fabric.mod.json") {
        expand("version" to project.version)
    }
}

apiValidation {
    ignoredProjects.addAll(subprojects.map { it.name })
    nonPublicMarkers.add("org.jetbrains.annotations.ApiStatus\$Internal")
}

java.withSourcesJar()

publishing.publications.named<MavenPublication>("maven") {
    artifactId = "elementa"
}

publishing {
    repositories {

        maven {
            name = "diamond"

            url = uri("https://maven.dediamondpro.dev/releases")

            credentials {
                username = System.getenv("MAVEN_DIAMOND_USER")
                password = System.getenv("MAVEN_DIAMOND_PASSWORD")
            }

            version = project.version
        }
    }
}