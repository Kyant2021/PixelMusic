dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        jcenter()
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://androidx.dev/snapshots/builds/7172183/artifacts/repository")
        maven("https://jitpack.io")
    }
}
rootProject.name = "Pixel Music"
include(":app")
