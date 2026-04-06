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
            api(projects.miuixCore)
            implementation(libs.jetbrains.compose.foundation)
        }
    }
}
