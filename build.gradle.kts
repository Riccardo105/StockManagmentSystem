plugins {
    id("java")
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("application")
}

javafx {
    version = "22"
    modules = listOf("javafx.controls", "javafx.fxml")
}
group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sourceSets {
    getByName("test") {
        java.srcDirs("src/test/java")
        resources.srcDirs("src/test/resources")
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.5.0")
    implementation ("com.google.inject:guice:5.0.1")
    implementation ("org.springframework.boot:spring-boot-starter-security:3.4.4")
    implementation ("com.google.guava:guava:32.1.3-jre")
    implementation ("org.hibernate:hibernate-core:5.6.10.Final")
    implementation ("mysql:mysql-connector-java:8.0.26")
    implementation("io.github.cdimascio:dotenv-java:2.3.2")
    implementation ("jakarta.persistence:jakarta.persistence-api:3.1.0")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("org.example.Main")
}

tasks.withType<JavaCompile> {
    options.release.set(21)
}