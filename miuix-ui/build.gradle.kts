// Copyright 2025, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

plugins {
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlinMultiplatform)
    id("module.publication")
}

kotlin {
    mingwX64()

    sourceSets {
        commonMain.dependencies {
            api(projects.miuixCore)
            api(libs.jetbrains.compose.foundation)
            api(projects.miuixShapes)

            implementation(libs.jetbrains.androidx.navigationevent)
            implementation(libs.jetbrains.compose.window.size)

            implementation(libs.materialKolor.utilities) // Material Color for Multiplatform
        }

        val skikoMain by creating {
            dependsOn(commonMain.get())
        }

        mingwX64Main {
            dependsOn(skikoMain)
        }
    }
}
