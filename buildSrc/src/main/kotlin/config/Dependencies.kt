// Requires package to allow nested object access in build.gradle
// https://github.com/handstandsam/AndroidDependencyManagement/issues/4
package config

object Builds {
    private const val androidGradlePlugin =
        "com.android.tools.build:gradle:${BuildGradlePluginsVersions.gradlePluginVersion}"
    private const val kotlinGradlePlugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${BuildGradlePluginsVersions.kotlinVersion}"
    private const val novodaGradlePlugin =
        "com.novoda:bintray-release:${BuildGradlePluginsVersions.novodaPluginVersion}"

    val basePlugins = listOf(
        androidGradlePlugin,
        kotlinGradlePlugin,
        novodaGradlePlugin
    )
}

object Libs {

    object Dagger {

        object Kapt {
            const val daggerCompiler =
                "com.google.dagger:dagger-compiler:${LibVersions.daggerVersion}"
            const val daggerProcessor =
                "com.google.dagger:dagger-android-processor:${LibVersions.daggerVersion}"
        }

        const val dagger = "com.google.dagger:dagger:${LibVersions.daggerVersion}"
        const val daggerAndroid = "com.google.dagger:dagger-android:${LibVersions.daggerVersion}"
        const val daggerAndroidX =
            "com.google.dagger:dagger-android-support:${LibVersions.daggerVersion}"
    }

    object AndroidX {

        object Arch {
            const val core =
                "androidx.arch.core:core-common:${AndroidXVersions.ArchComponent.androidXArchCoreVersion}"

            object Lifecycle {

                object Kapt {
                    const val compiler =
                        "androidx.lifecycle:lifecycle-compiler:${AndroidXVersions.ArchComponent.androidXArchLifecycleVersion}"
                }

                const val common =
                    "androidx.lifecycle:lifecycle-common-java8:${AndroidXVersions.ArchComponent.androidXArchLifecycleVersion}"
                const val runtime =
                    "androidx.lifecycle:lifecycle-runtime:${AndroidXVersions.ArchComponent.androidXArchLifecycleVersion}"
                const val extensions =
                    "androidx.lifecycle:lifecycle-extensions:${AndroidXVersions.ArchComponent.androidXArchLifecycleVersion}"

                // Livedata
                const val livedataCore =
                    "androidx.lifecycle:lifecycle-livedata-core:${AndroidXVersions.ArchComponent.androidXArchLiveDataVersion}"
                const val livedata =
                    "androidx.lifecycle:lifecycle-livedata:${AndroidXVersions.ArchComponent.androidXArchLiveDataVersion}"

                const val viewmodel =
                    "androidx.lifecycle:lifecycle-viewmodel:${AndroidXVersions.ArchComponent.androidXArchViewModelVersion}"
                // RxJava support for LiveData
                const val reactivestreams =
                    "androidx.lifecycle:lifecycle-reactivestreams:${AndroidXVersions.ArchComponent.androidXArchLifecycleVersion}"
            }
        }

        const val core = "androidx.core:core:${AndroidXVersions.androidXCoreVersion}"
        const val coreKtx = "androidx.core:core-ktx:${AndroidXVersions.androidXCoreKtxVersion}"
        const val appcompat =
            "androidx.appcompat:appcompat:${AndroidXVersions.androidXAppCompatVersion}"
    }

    const val kotlin =
        "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${BuildGradlePluginsVersions.kotlinVersion}"
}

object TestingLibs {
    object AndroidX {
        const val core = "androidx.test:core:1.1.0"
        const val runner = "androidx.test:runner:1.1.1"
        const val rules = "androidx.test:rules:1.1.1"

        // Assertions
        const val extJUnit = "androidx.test.ext:junit:1.1.0"
        const val extTruth = "androidx.test.ext:truth:1.1.0"

        const val uiAutomator =
            "androidx.test.uiautomator:uiautomator:${TestingVersions.androidXTestUiAutomatorVersion}"

        const val orchestrator =
            "androidx.test:orchestrator:${TestingVersions.androidXTestOrchestratorVersion}"

        object Espresso {
            const val core =
                "androidx.test.espresso:espresso-core:${TestingVersions.androidXTestEspressoVersion}"
            const val contrib =
                "androidx.test.espresso:espresso-contrib:${TestingVersions.androidXTestEspressoVersion}"
            const val intents =
                "androidx.test.espresso:espresso-intents:${TestingVersions.androidXTestEspressoVersion}"
            const val web =
                "androidx.test.espresso:espresso-web:${TestingVersions.androidXTestEspressoVersion}"
            const val idlingResource =
                "androidx.test.espresso:espresso-idling-resource:${TestingVersions.androidXTestEspressoVersion}"
            const val concurrent =
                "androidx.test.espresso.idling:idling-concurrent:${TestingVersions.androidXTestEspressoVersion}"
        }
    }

    const val junit = "junit:junit:${TestingVersions.testJUnitVersion}"
    const val truth = "com.google.truth:truth:${TestingVersions.testGoogleTruthVersion}"
    const val robolectric = "org.robolectric:robolectric:${TestingVersions.testRobolectricVersion}"
}


