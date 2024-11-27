pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            version("room", "2.6.1")
            version("hilt", "2.50")
            
            // Room
            library("androidx-room-runtime", "androidx.room", "room-runtime").versionRef("room")
            library("androidx-room-ktx", "androidx.room", "room-ktx").versionRef("room")
            library("androidx-room-compiler", "androidx.room", "room-compiler").versionRef("room")
            
            // Hilt
            library("hilt-android", "com.google.dagger", "hilt-android").versionRef("hilt")
            library("hilt-compiler", "com.google.dagger", "hilt-compiler").versionRef("hilt")
            library("androidx-hilt-navigation-compose", "androidx.hilt", "hilt-navigation-compose").version("1.1.0")
            
            // Navigation
            library("androidx-navigation-compose", "androidx.navigation", "navigation-compose").version("2.7.6")
            
            // Plugins
            plugin("hilt-android", "com.google.dagger.hilt.android").versionRef("hilt")
            plugin("kotlin-kapt", "org.jetbrains.kotlin.kapt").version("1.9.21")
        }
    }
}

rootProject.name = "My Application"
include(":app")
 