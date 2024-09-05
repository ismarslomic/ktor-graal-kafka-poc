val kotlinVersion: String by project
val logbackVersion: String by project
val prometheusVersion: String by project

plugins {
    kotlin("jvm") version "2.0.20"
    id("io.ktor.plugin") version "2.3.12"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.20"
    id("org.graalvm.buildtools.native") version "0.10.2"
    id("com.github.ben-manes.versions") version "0.51.0"
}

group = "trafikk.ruter.no"
version = "0.0.1"

application {
    mainClass.set("trafikk.ruter.no.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-metrics-micrometer-jvm")
    implementation("io.micrometer:micrometer-registry-prometheus:$prometheusVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-cio-jvm")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-server-config-yaml")
    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}

// See https://graalvm.github.io/native-build-tools/0.10.2/gradle-plugin.html
graalvmNative {
    binaries {

        named("main") {
            fallback = false // Sets the fallback mode of native-image, defaults to false
            verbose = true // Add verbose output, defaults to false
            imageName = "graalvm-server"

            buildArgs.addAll(
                "--initialize-at-build-time=ch.qos.logback",
                "--initialize-at-build-time=ch.qos.logback.classic.Logger",

                "--initialize-at-build-time=org.slf4j.helpers.SubstituteServiceProvider",
                "--initialize-at-build-time=org.slf4j.helpers.SubstituteLoggerFactory",
                "--initialize-at-build-time=org.slf4j.helpers.SubstituteLogger",
                "--initialize-at-build-time=org.slf4j.helpers.NOP_FallbackServiceProvider",
                "--initialize-at-build-time=org.slf4j.helpers.NOPLoggerFactory",
                "--initialize-at-build-time=org.slf4j.LoggerFactory",

                "--initialize-at-build-time=kotlinx.serialization.json.JsonImpl",
                "--initialize-at-build-time=kotlinx.serialization.json.JsonConfiguration",
                "--initialize-at-build-time=kotlinx.serialization.modules.SerialModuleImpl",
                "--initialize-at-build-time=kotlinx.serialization.json.internal.DescriptorSchemaCache",
                "--initialize-at-build-time=org.xml.sax.helpers.LocatorImpl",
                "--initialize-at-build-time=org.xml.sax.helpers.AttributesImpl",

                "--initialize-at-build-time=kotlinx.coroutines.CoroutineName",
                "--initialize-at-build-time=kotlinx.coroutines.CoroutineName\$Key",
                "--initialize-at-build-time=io.ktor,kotlin",
                "--initialize-at-build-time=kotlinx.io.Buffer",
                "--initialize-at-build-time=kotlinx.io.Segment",
                "--initialize-at-build-time=kotlinx.io.Segment\$Companion",

                "-H:+InstallExitHandlers",
                "-H:+ReportUnsupportedElementsAtRuntime",
                "-H:+ReportExceptionStackTraces",

                // "-H:IncludeResources=application.yaml" // Not working with GraalVM, see KTOR-3453
            )
        }

        // See https://graalvm.github.io/native-build-tools/0.10.2/gradle-plugin.html#testing-support
        named("test") {
            imageName = "graalvm-test-server"
            fallback = false
            verbose = true

            val resourcePath = "${projectDir}/src/test/resources/META-INF/native-image/"

            buildArgs.addAll(
                "--initialize-at-build-time=ch.qos.logback",
                "--initialize-at-build-time=ch.qos.logback.classic.Logger",

                "--initialize-at-build-time=org.slf4j.helpers.SubstituteServiceProvider",
                "--initialize-at-build-time=org.slf4j.helpers.SubstituteLoggerFactory",
                "--initialize-at-build-time=org.slf4j.helpers.SubstituteLogger",
                "--initialize-at-build-time=org.slf4j.helpers.NOP_FallbackServiceProvider",
                "--initialize-at-build-time=org.slf4j.helpers.NOPLoggerFactory",
                "--initialize-at-build-time=org.slf4j.LoggerFactory",

                "--initialize-at-build-time=kotlinx.serialization.json.JsonImpl",
                "--initialize-at-build-time=kotlinx.serialization.json.JsonConfiguration",
                "--initialize-at-build-time=kotlinx.serialization.modules.SerialModuleImpl",
                "--initialize-at-build-time=kotlinx.serialization.json.internal.DescriptorSchemaCache",
                "--initialize-at-build-time=org.xml.sax.helpers.LocatorImpl",
                "--initialize-at-build-time=org.xml.sax.helpers.AttributesImpl",

                "--initialize-at-build-time=kotlinx.coroutines.CoroutineName",
                "--initialize-at-build-time=kotlinx.coroutines.CoroutineName\$Key",
                "--initialize-at-build-time=io.ktor,kotlin",
                "--initialize-at-build-time=kotlinx.io.Buffer",
                "--initialize-at-build-time=kotlinx.io.Segment",
                "--initialize-at-build-time=kotlinx.io.Segment\$Companion",

                "-H:+InstallExitHandlers",
                "-H:+ReportUnsupportedElementsAtRuntime",
                "-H:+ReportExceptionStackTraces",

                "-H:ReflectionConfigurationFiles=${resourcePath}reflect-config.json",
                "-H:ResourceConfigurationFiles=${resourcePath}resource-config.json",
            )
        }
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}
