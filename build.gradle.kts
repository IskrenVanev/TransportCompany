plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sourceSets {
    main {
        java.srcDirs("src/main/java")
    }
    test {
        java.srcDirs("src/test/java")
    }
}

dependencies {
    implementation("org.mpierce:jfixture-hibernate:1.0.1")
    implementation("junit:junit:4.13.2")
    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.hibernate.orm:hibernate-core:6.3.1.Final")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.apache.poi:poi:5.2.2")
    implementation("org.apache.poi:poi-ooxml:5.2.2")
    testImplementation("junit:junit:4.13.1")
    testImplementation("org.mockito:mockito-core:3.9.0")
}

tasks.test {
    useJUnitPlatform()
}