plugins {
    id("java")
    id("maven-publish")
}

version = project.property("version") as String
group = project.property("maven_group") as String

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

repositories {
    mavenCentral()
}


dependencies {

}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(21)
}
