package trafikk.ruter.no

import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import trafikk.ruter.no.plugins.configureMonitoring
import trafikk.ruter.no.plugins.configureRouting
import trafikk.ruter.no.plugins.configureSerialization

fun main(args: Array<String>) {
    // See https://ktor.io/docs/server-engines.html#choose-create-server
    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureMonitoring()
        configureSerialization()
        configureRouting()
    }.start(wait = true)
}
