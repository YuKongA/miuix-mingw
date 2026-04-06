// Copyright 2026, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

plugins {
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlinMultiplatform)
    id("module.publication")
}

kotlin {
    mingwX64()

    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.jetbrains.compose.foundation)
        }

        val skikoMain by creating {
            dependsOn(commonMain.get())
        }

        mingwX64Main {
            dependsOn(skikoMain)
        }
    }
}
