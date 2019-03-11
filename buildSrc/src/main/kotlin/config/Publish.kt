package config

object ArtifactInfo {

    const val groupProjectID = "org.ymkm.android"
    const val websiteURL = "https://www.github.com/ymkm/kmvp"

    object Core {

        const val artifactProjectID = "kmvp"
        const val publishVersionID = "1.0.0"
        const val description = "A simple MVP library written in Kotlin."
    }

    object Dagger {

        const val artifactProjectID = "kmvp-dagger"
        const val publishVersionID = "1.0.0"
        const val description = "A dagger 2 extension for KMVP that uses dependency injection to inject presenter into activities/fragments"
    }

    object Lifecycle {

        const val artifactProjectID = "kmvp-lifecycle"
        const val publishVersionID = "1.0.0"
        const val description = "A KMVP extension that provides lifecycle-aware presenter"
    }

    object DaggerLifecycle {

        const val artifactProjectID = "kmvp-dagger-lifecycle"
        const val publishVersionID = "1.0.0"
        const val description = "A dagger 2 extension for KMVP that merges kmvp-lifecycle and kmvp-dagger"
    }

    const val bintrayUserOrg = "yochiro"
    const val bintrayRepo = "android"
}