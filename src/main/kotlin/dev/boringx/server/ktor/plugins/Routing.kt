package dev.boringx.server.ktor.plugins

import dev.boringx.resources.values.helloString
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.http.content.staticResources
import io.ktor.server.response.respondText
import io.ktor.server.routing.routing
import io.ktor.server.routing.get

fun Application.configureRouting() {
    routing {
        get("/") { call.respondText(helloString) }

        staticResources("/", "/")
    }
}
