import nu.studer.gradle.jooq.JooqEdition.OSS
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR
import org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT
import org.gradle.api.tasks.testing.logging.TestLogEvent.STARTED
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.lang.System.getenv

plugins {
    id("io.spring.dependency-management") version "1.0.13.RELEASE"
    id("org.springframework.boot") version "2.7.4"
    id("org.flywaydb.flyway") version "8.5.13"
    id("nu.studer.jooq") version "7.1.1"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
    kotlin("plugin.allopen") version "1.6.21"
    kotlin("kapt") version "1.6.21"
    groovy
}

group = "tk.aizydorczyk.kashubian"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

configurations {
    all {
        exclude("org.springframework.boot", "spring-boot-starter-logging")
    }
}

dependencies {
    implementation("org.flywaydb:flyway-core:9.2.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude("org.springframework.boot", "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("com.vladmihalcea:hibernate-types-4:2.18.0")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.mapstruct:mapstruct:1.5.2.Final")
    kapt("org.mapstruct:mapstruct-processor:1.5.2.Final")
    implementation("org.springframework.boot:spring-boot-starter-jetty")
    implementation("de.codecentric:spring-boot-admin-starter-client:2.7.4")
    implementation("de.codecentric:spring-boot-admin-server-ui:2.7.4")
    implementation("de.codecentric:spring-boot-admin-starter-server:2.7.4")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.postgresql:postgresql:42.5.0")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.11")
    implementation("com.graphql-java:graphql-java-extended-scalars:18.1")
    implementation("org.jeasy:easy-random-core:5.0.0")
    implementation("com.github.javafaker:javafaker:1.0.2")
    implementation("org.yaml:snakeyaml:1.28")
    implementation("org.flywaydb:flyway-core:8.5.13")
    compileOnly("org.jooq:jooq-codegen-maven:3.17.3")
    jooqGenerator("jakarta.xml.bind:jakarta.xml.bind-api:4.0.0")
    jooqGenerator("org.postgresql:postgresql:42.5.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.spockframework:spock-core:2.0-M2-groovy-3.0")
    testImplementation("org.spockframework:spock-spring:2.0-M2-groovy-3.0")
    testImplementation("org.codehaus.groovy:groovy-all:3.0.13")
    testImplementation("org.testcontainers:testcontainers:1.17.3")
    testImplementation("org.testcontainers:spock:1.17.3")
    testImplementation("org.testcontainers:postgresql:1.17.3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.bootRun {
    jvmArgs = listOf("-Xms1g", "-Xmx2g")
}

tasks.bootJar {
    archiveFileName.set("${archiveBaseName.get()}.${archiveExtension.get()}")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events(STARTED,
                FAILED,
                PASSED,
                SKIPPED,
                STANDARD_ERROR,
                STANDARD_OUT)
        exceptionFormat = TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
        debug {
            events(STARTED,
                    FAILED,
                    PASSED,
                    SKIPPED,
                    STANDARD_ERROR,
                    STANDARD_OUT)
            exceptionFormat = TestExceptionFormat.FULL
        }
        info.events = debug.events
        info.exceptionFormat = debug.exceptionFormat

        val failedTests = mutableListOf<TestDescriptor>()
        val skippedTests = mutableListOf<TestDescriptor>()
        addTestListener(object : TestListener {
            override fun beforeSuite(suite: TestDescriptor) {}
            override fun beforeTest(testDescriptor: TestDescriptor) {}
            override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {
                when (result.resultType) {
                    TestResult.ResultType.FAILURE -> failedTests.add(testDescriptor)
                    TestResult.ResultType.SKIPPED -> skippedTests.add(testDescriptor)
                }
            }

            override fun afterSuite(suite: TestDescriptor, result: TestResult) {
                if (suite.parent == null) { // root suite
                    logger.lifecycle("----")
                    logger.lifecycle("Test result: ${result.resultType}")
                    logger.lifecycle("Test summary: ${result.testCount} tests, " +
                            "${result.successfulTestCount} succeeded, " +
                            "${result.failedTestCount} failed, " +
                            "${result.skippedTestCount} skipped")
                    if (failedTests.isNotEmpty()) {
                        logger.lifecycle("\tFailed Tests:")
                        failedTests.forEach {
                            parent?.let { parent ->
                                logger.lifecycle("\t\t${parent.name} - ${it.name}")
                            } ?: logger.lifecycle("\t\t${it.name}")
                        }
                    }

                    if (skippedTests.isNotEmpty()) {
                        logger.lifecycle("\tSkipped Tests:")
                        skippedTests.forEach {
                            parent?.let { parent ->
                                logger.lifecycle("\t\t${parent.name} - ${it.name}")
                            } ?: logger.lifecycle("\t\t${it.name}")
                        }
                    }
                }
            }
        })
    }
}

kapt {
    correctErrorTypes = true
}

flyway {
    locations = arrayOf("filesystem:./src/main/resources/db/migration")
    driver = "org.postgresql.Driver"
    url = getenv("DB_URL")
    user = getenv("DB_USER")
    password = getenv("DB_PASSWORD")
}


jooq {
    version.set("3.17.3")
    edition.set(OSS)

    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(true)

            jooqConfiguration.apply {
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = getenv("DB_URL")
                    user = getenv("DB_USER")
                    password = getenv("DB_PASSWORD")
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                        excludes = "flyway_schema_history"
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = true
                        isFluentSetters = true
                    }
                    target.apply {
                        packageName = "tk.aizydorczyk.kashubian.crud.model.entitysearch"
                        directory = "build/generated/source/jooq/main"
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}

tasks {
    named("generateJooq") {
        dependsOn(flywayMigrate)
    }

}

