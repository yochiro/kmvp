plugins {
    `kotlin-dsl`
}

buildscript {
    repositoriesFrom(build.Repos.buildRepoUrls)
    repositories.jcenter()
    repositories.google()
    dependenciesFrom(build.Builds.basePlugins)
}

subprojects {
    buildscript {
        repositories.jcenter()
        repositories.google()
        repositoriesFrom(build.Repos.buildRepoUrls)
    }
    repositories.jcenter()
    repositories.google()
    repositoriesFrom(build.Repos.dependenciesRepoUrls)
}

repositories {
    jcenter()
}

enum class ModuleType(val suffix: String) { LIBRARY("lib"), FEATURE("feature") }

fun buildScriptPath(moduleType: ModuleType) =
    File(baseBuildScriptPath, "build_${moduleType.suffix}.gradle")

val baseBuildScriptPath by extra { file("build_scripts") }
val libModuleBuildScriptFile by extra { buildScriptPath(ModuleType.LIBRARY) }
val daggerBuildScriptFile by extra { File(baseBuildScriptPath, "dagger.gradle") }
val publishCoreBuildScriptFile by extra { File(baseBuildScriptPath, "publish_core.gradle") }
val publishDaggerBuildScriptFile by extra { File(baseBuildScriptPath, "publish_dagger.gradle") }
val publishLifecycleBuildScriptFile by extra { File(baseBuildScriptPath, "publish_lifecycle.gradle") }
