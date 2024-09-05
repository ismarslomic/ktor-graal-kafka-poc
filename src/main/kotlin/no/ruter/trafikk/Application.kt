package no.ruter.trafikk

import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import no.ruter.trafikk.plugins.configureMonitoring
import no.ruter.trafikk.plugins.configureRouting
import no.ruter.trafikk.plugins.configureSerialization

fun main() {
    // See https://ktor.io/docs/server-engines.html#choose-create-server
    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureMonitoring()
        configureSerialization()
        configureRouting()
    }.start(wait = true)
}
