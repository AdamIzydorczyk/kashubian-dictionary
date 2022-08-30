import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("io.spring.dependency-management") version "1.0.13.RELEASE"
    id("org.springframework.boot") version "2.7.2"
    id("org.flywaydb.flyway") version "8.5.13"
    id("nu.studer.jooq") version "7.1.1"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
    kotlin("plugin.allopen") version "1.6.21"
    kotlin("kapt") version "1.6.21"
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
    implementation("org.flywaydb:flyway-core:8.5.13")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude("org.springframework.boot", "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.boot:spring-boot-starter-jooq:2.7.3")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    implementation("com.vladmihalcea:hibernate-types-4:2.17.3")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.mapstruct:mapstruct:1.5.2.Final")
    kapt("org.mapstruct:mapstruct-processor:1.5.2.Final")
    implementation("org.springframework.boot:spring-boot-starter-jetty")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.postgresql:postgresql:42.5.0")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.jeasy:easy-random-core:5.0.0")
    implementation("com.github.javafaker:javafaker:1.0.2")
    implementation("org.yaml:snakeyaml:1.28")
    compileOnly("org.jooq:jooq-codegen-maven:3.17.3")
    jooqGenerator("jakarta.xml.bind:jakarta.xml.bind-api:4.0.0")
    jooqGenerator("org.postgresql:postgresql:42.5.0")
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

tasks.withType<Test> {
    useJUnitPlatform()
}

flyway {
    locations = arrayOf("filesystem:./src/main/resources/db/migration")
    driver = "org.postgresql.Driver"
    url = "jdbc:postgresql://localhost:5555/kas"
    user = "kas"
    password = "kas"
}


jooq {
    version.set("3.17.3")  // default (can be omitted)
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)  // default (can be omitted)

    configurations {
        create("main") {  // name of the jOOQ configuration
            generateSchemaSourceOnCompilation.set(true)  // default (can be omitted)

            jooqConfiguration.apply {
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:5555/kas"
                    user = "kas"
                    password = "kas"
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


