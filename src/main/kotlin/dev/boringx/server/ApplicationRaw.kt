package dev.boringx.server

import RESOURCES_PATH
import SERVER_PORT
import dev.boringx.sendResponse200
import dev.boringx.sendResponse404
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
            output.sendResponse200(data = file.readBytes())
        } catch (exception: Exception) {
            output.sendResponse404(exception)
        } finally {
            output.flush()
            output.close()
            connectionSocket.close()
        }
    }
}