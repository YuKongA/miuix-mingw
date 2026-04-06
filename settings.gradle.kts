// Copyright 2025, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

@file:Suppress("UnstableApiUsage")

rootProject.name = "miuix-mingw"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-plugins")
    repositories {
        mavenLocal()
        maven {
            name = "compose-mingw-maven-repository"
            setUrl("https://raw.githubusercontent.com/YuKongA/compose-mingw_maven-repository/main/repository/releases")
        }
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        maven {
            name = "compose-mingw-maven-repository"
            setUrl("https://raw.githubusercontent.com/YuKongA/compose-mingw_maven-repository/main/repository/releases")
        }
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(":miuix-core")
include(":miuix-ui")
include(":miuix-preference")
include(":miuix-blur")
include(":miuix-icons")
include(":miuix-navigation3-ui")
include(":miuix-shapes")

include(":example:shared")
include(":example:mingw")
