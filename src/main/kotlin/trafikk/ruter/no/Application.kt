package trafikk.ruter.no

import io.ktor.server.application.Application
import trafikk.ruter.no.plugins.configureMonitoring
import trafikk.ruter.no.plugins.configureRouting
import trafikk.ruter.no.plugins.configureSerialization

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureMonitoring()
    configureSerialization()
    configureRouting()
}
