package no.tet.sandbox

import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import no.tet.sandbox.plugins.configureMonitoring
import no.tet.sandbox.plugins.configureRouting
import no.tet.sandbox.plugins.configureSerialization

fun main() {
    // See https://ktor.io/docs/server-engines.html#choose-create-server
    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureMonitoring()
        configureSerialization()
        configureRouting()
    }.start(wait = true)
}
