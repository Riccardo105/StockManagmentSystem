plugins {
    id("java")
    id ("co.uzzu.dotenv.gradle") version "4.0.0"
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
    implementation ("org.hibernate:hibernate-core:5.6.10.Final")
    implementation ("mysql:mysql-connector-java:8.0.26")
    implementation ("jakarta.persistence:jakarta.persistence-api:3.1.0")
}



tasks.test {
    useJUnitPlatform()
}

tasks.register("checkDatabaseUrl") {
    doLast {
        val databaseUrl = System.getenv("DATABASE_URL")
        if (databaseUrl != null) {
            println("DATABASE_URL is set to: $databaseUrl")
        } else {
            println("DATABASE_URL is not set!")
        }
    }
}