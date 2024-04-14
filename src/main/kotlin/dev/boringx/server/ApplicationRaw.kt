package dev.boringx.server

import RESOURCES_PATH
import SERVER_PORT
import dev.boringx.server.resources.values.FileNotFound
import dev.boringx.server.resources.values.NOT_FOUND_404
import dev.boringx.server.resources.values.OK_200
import java.io.File
import java.net.ServerSocket


fun main() {
    val serverSocket = ServerSocket(SERVER_PORT)
    println("[INFO] Application - Responding at http://${serverSocket.localSocketAddress}")
    while (true) {
        val connectionSocket = serverSocket.accept()
        val output = connectionSocket.getOutputStream()

        try {
            val input = connectionSocket.getInputStream().bufferedReader()
            val request = input.readLine() // format ~: "GET /index.html HTTP/1.1"
            val requestedPathName = request.split(" ")[1]
            val file = File(RESOURCES_PATH + requestedPathName)
            val outputData = file.readBytes()

            output.write(OK_200.toByteArray())
            output.write(outputData)
            println("[TRACE] Application found route.")
        } catch (e: Exception) {
            output.write(NOT_FOUND_404.toByteArray())
            output.write(FileNotFound.toByteArray())
            println("[TRACE] Application not found route. Exception: ${e.stackTraceToString()}")
        } finally {
            output.flush()
            output.close()
            connectionSocket.close()
        }
    }
}