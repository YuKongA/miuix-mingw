// Copyright 2025, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

plugins {
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlinMultiplatform)
}

val generatedSrcDir: Provider<Directory> = layout.buildDirectory.dir("generated/miuix-example")

kotlin {
    mingwX64()

    sourceSets {
        commonMain {
            kotlin.srcDir(generatedSrcDir.map { it.dir("kotlin") })
            dependencies {
                api(projects.miuixUi)
                api(projects.miuixPreference)
                api(libs.jetbrains.compose.components.resources)
                implementation(projects.miuixBlur)
                implementation(projects.miuixIcons)
                implementation(projects.miuixNavigation3Ui)
                implementation(projects.miuixShapes)
                implementation(libs.androidx.navigation3.runtime)
                implementation(libs.jetbrains.androidx.navigationevent)
            }
        }
    }
}

val generateVersionInfo by tasks.registering(GenerateVersionInfoTask::class) {
    versionName.set(BuildConfig.APPLICATION_VERSION_NAME)
    versionCode.set(getGitVersionCode())
    outputFile.set(generatedSrcDir.map { it.file("kotlin/misc/VersionInfo.kt") })
    iosPlistFile.set(layout.projectDirectory.file("../ios/iosApp/Info.plist"))
}

tasks.named("generateComposeResClass").configure {
    dependsOn(generateVersionInfo)
}
