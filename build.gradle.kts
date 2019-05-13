import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    idea
    `kotlin-dsl`
}

idea {
    module {
        name = project.path.substring(1).split(':').joinToString("-")
    }
}

buildscript {
    repositoriesFrom(config.Repos.buildRepoUrls)
    repositories.jcenter()
    repositories.google()
    dependenciesFrom(config.Builds.basePlugins)

    dependencies.classpath("com.novoda:bintray-release:0.9.1")
}

repositories {
    jcenter()
}
