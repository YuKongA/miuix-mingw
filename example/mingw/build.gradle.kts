// Copyright 2026, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

plugins {
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlinMultiplatform)
}

// Windows application icon (.ico → .res.o via windres, linked into exe)
val windowsIcon = project.file("resources/icon.ico")
val resObjDir = layout.buildDirectory.dir("windowsRes")
val resObjFile = resObjDir.map { it.file("app.res.o") }

val compileWindowsResource by tasks.registering(Exec::class) {
    inputs.file(windowsIcon)
    outputs.file(resObjFile)
    val iconFile = windowsIcon
    val objFile = resObjFile.get().asFile
    val rcFile = resObjDir.get().file("app.rc").asFile
    doFirst {
        objFile.parentFile.mkdirs()
        iconFile.copyTo(objFile.parentFile.resolve("icon.ico"), overwrite = true)
        objFile.parentFile.resolve("app.rc").writeText("1 ICON \"icon.ico\"")
    }
    commandLine("windres", rcFile.absolutePath, "-o", objFile.absolutePath)
}

kotlin {
    mingwX64 {
        binaries.executable {
            entryPoint = "main"
            baseName = "Miuix"
            linkerOpts(resObjFile.get().asFile.absolutePath)
            linkerOpts("-mwindows")
            freeCompilerArgs = buildList {
                if (!debuggable) add("-opt")
                add("-Xbinary=gcSchedulerType=adaptive")
            }
        }
    }

    sourceSets {
        mingwX64Main {
            dependencies {
                implementation(projects.example.shared)
            }
        }
    }
}

// Copy ANGLE DLLs and compose resources alongside the executable
val angleDllDir = layout.projectDirectory.dir("libs")
val sharedResourcesDir = rootProject.layout.projectDirectory
    .dir("example/shared/build/generated/compose/resourceGenerator/preparedResources/commonMain/composeResources")

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink>().configureEach {
    dependsOn(compileWindowsResource)
    val dllFiles = angleDllDir.asFile.listFiles()?.filter { it.extension == "dll" } ?: emptyList()
    val resSource = sharedResourcesDir.asFile
    doLast {
        val outputDir = outputFile.get().parentFile
        dllFiles.forEach { it.copyTo(outputDir.resolve(it.name), overwrite = true) }
        if (resSource.exists()) {
            resSource.copyRecursively(outputDir.resolve("compose-resources"), overwrite = true)
        }
    }
}
