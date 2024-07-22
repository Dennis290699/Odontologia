plugins {
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.postgresql:postgresql:42.7.3")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
