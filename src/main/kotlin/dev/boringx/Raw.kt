package dev.boringx

import dev.boringx.resources.values.FileNotFound
import dev.boringx.resources.values.NOT_FOUND_404
import dev.boringx.resources.values.OK_200
import java.io.File
import java.net.ServerSocket

private const val RESOURCES_PATH = "src/main/resources"

fun main() {
    val serverSocket = ServerSocket(8080)

    while (true) {
        val connectionSocket = serverSocket.accept()
        val output = connectionSocket.getOutputStream()

        try {
            val input = connectionSocket.getInputStream().bufferedReader()
            val request = input.readLine() // format ~: GET /index.html HTTP/1.1
            val requestedPathName = request.split(" ")[1]
            val file = File(RESOURCES_PATH + requestedPathName)
            val outputData = file.readBytes()
            println("Hello")

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