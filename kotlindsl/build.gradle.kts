import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
//    id("org.springframework.boot") version "2.7.0"
//    id("io.spring.dependency-management") version "1.0.11.RELEASE"
//    id("java")
//    id("org.jetbrains.kotlin.jvm") version "1.6.21"
//    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.21"
//    kotlin("plugin.noarg") version "1.6.21"
    id("org.springframework.boot") version "2.6.7"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

//allOpen {
//    annotations("org.springframework.boot.autoconfigure.SpringBootApplication")
//}

//noArg {
//    annotation("javax.persistence.Entity")
//}

group = "com.ansj"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

//configurations {
//    compileOnly {
//        extendsFrom(configurations.annotationProcessor.get())
//    }
//}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // serialized 하기 위해서 추가.
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    // 필수적으로 들어가는 의존성
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.ninja-squad:springmockk:3.1.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}