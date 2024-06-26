plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.testng:testng:7.10.2")
    implementation("io.appium:java-client:9.2.3")
    implementation("org.seleniumhq.selenium:selenium-java:4.22.0")
    implementation("org.apache.commons:commons-lang3:3.14.0")
    implementation("io.github.bonigarcia:webdrivermanager:5.8.0")
    implementation("org.yaml:snakeyaml:2.2")
    implementation("org.slf4j:slf4j-api:2.0.13")
    implementation("ch.qos.logback:logback-classic:1.5.6")
}

tasks.withType<Test> {
    useTestNG()
}
