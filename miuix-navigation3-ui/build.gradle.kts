// Copyright 2025, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

plugins {
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlinMultiplatform)
    id("module.publication")
}

kotlin {
    mingwX64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.collection)
            implementation(libs.androidx.navigation3.runtime)
            implementation(libs.jetbrains.androidx.navigationevent)
            implementation(libs.jetbrains.compose.foundation)
            implementation(libs.jetbrains.lifecycle.runtime)
            implementation(libs.jetbrains.lifecycle.runtime.compose)
            implementation(projects.miuixShapes)
        }

        val skikoMain by creating {
            dependsOn(commonMain.get())
        }

        mingwX64Main {
            dependsOn(skikoMain)
        }
    }
}
