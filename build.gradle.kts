import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.2"
	id("io.spring.dependency-management") version "1.0.12.RELEASE"
	id("org.flywaydb.flyway") version "8.5.13"

	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
	kotlin("plugin.allopen") version "1.6.21"
	kotlin("kapt") version "1.6.21"
}

group = "tk.aizydorczyk.kashubian"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.flywaydb:flyway-core:8.5.13")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web") {
		exclude("org.springframework.boot", "spring-boot-starter-tomcat")
	}

	implementation("com.introproventures:graphql-jpa-query-boot-starter:0.4.19"){
		exclude("com.graphql-java","graphql-java")
	}
	implementation("com.introproventures:graphql-jpa-query-graphiql:0.4.19"){
		exclude("com.graphql-java","graphql-java")
	}
	implementation("com.graphql-java:graphql-java:13.0")
	implementation("com.vladmihalcea:hibernate-types-4:2.17.3")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.mapstruct:mapstruct:1.5.2.Final")
	kapt("org.mapstruct:mapstruct-processor:1.5.2.Final")
	implementation("org.springframework.boot:spring-boot-starter-jetty")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.postgresql:postgresql:42.4.1")
	implementation("io.springfox:springfox-boot-starter:3.0.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.jeasy:easy-random-core:5.0.0")
	implementation("com.github.javafaker:javafaker:1.0.2")
	implementation("org.yaml:snakeyaml:1.28")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.bootRun {
	jvmArgs = listOf("-Xms1g", "-Xmx2g")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.getByName<Jar>("jar") {
	enabled = false
}
val compileKotlin: KotlinCompile by tasks