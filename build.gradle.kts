plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.mpierce:jfixture-hibernate:1.0.1")
    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.hibernate.orm:hibernate-core:6.3.1.Final")
    implementation ("mysql:mysql-connector-java:8.0.33")
    implementation ("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.apache.poi:poi:5.2.2") // Use the latest version available
    implementation("org.apache.poi:poi-ooxml:5.2.2") // Use the latest version available
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}