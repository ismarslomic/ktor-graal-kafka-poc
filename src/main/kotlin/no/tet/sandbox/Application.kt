package no.tet.sandbox

import io.ktor.server.application.Application
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import no.tet.sandbox.plugins.configureMonitoring
import no.tet.sandbox.plugins.configureRouting
import no.tet.sandbox.plugins.configureSerialization
import no.tet.sandbox.properties.KtorPropertiesHolder
import no.tet.sandbox.properties.loadProperties

fun main() {
    val ktorProperties = loadProperties<KtorPropertiesHolder>().ktor

    with(ktorProperties.deployment) {
        embeddedServer(
            CIO,
            port = port,
            host = host,
            module = Application::module,
        ).start(wait = true)
    }
}

fun Application.module() {
    configureMonitoring()
    configureSerialization()
    configureRouting()
}
