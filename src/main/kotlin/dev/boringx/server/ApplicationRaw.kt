package dev.boringx.server

import dev.boringx.server.resources.values.FileNotFound
import dev.boringx.server.resources.values.NOT_FOUND_404
import dev.boringx.server.resources.values.OK_200
import java.io.File
import java.net.ServerSocket

private const val RESOURCES_PATH = "src/main/resources"

fun main() {
    val serverSocket = ServerSocket(8080)
    println("Application - Responding at http://${serverSocket.localSocketAddress}")
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
        } catch (e: Exception) {
            output.write(NOT_FOUND_404.toByteArray())
            output.write(FileNotFound.toByteArray())
        } finally {
            output.flush()
            output.close()
            connectionSocket.close()
        }
    }
}