// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(libs.google.services) // Google Services plugin for Firebase
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
}
