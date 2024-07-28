plugins {
    id("java")
    id("application")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("org.jdatepicker:jdatepicker:1.3.4")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    // Configura el punto de entrada principal de tu aplicación
    mainClass.set("App.Principal")
}

tasks.test {
    useJUnitPlatform()
}
