package dev.boringx.proxy

import RESOURCES_PATH
import SERVER_PORT
import dev.boringx.server.resources.values.FileNotFound
import dev.boringx.server.resources.values.NOT_FOUND_404
import dev.boringx.server.resources.values.OK_200
import java.io.File
import java.io.InputStream
import java.net.ServerSocket
import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.pathString

private const val CACHE = "$RESOURCES_PATH/cache/"

fun main() {
    val serverSocket = ServerSocket(SERVER_PORT)
    println("[INFO] Application - Responding at http://${serverSocket.localSocketAddress}")
    while (true) {
        val connectionSocket = serverSocket.accept()
        val output = connectionSocket.getOutputStream()
        try {
            val input = connectionSocket.inputStream.bufferedReader()
            val request = input.readLine()
            println(request)

            // Извлекаем имя файла из сообщения
            val requestedPath = Path(request.split(" ")[1].removePrefix("/"))
            val proxyPath = Path(CACHE + request.split(" ")[1].removePrefix("/"))
            println("requestedPath: $requestedPath")

            val file = File(proxyPath.pathString)
            if (file.exists()) {
                println("Cache hit")
                val responseData = file.readBytes()

                output.write(OK_200.toByteArray())
                output.write(responseData)
                println("[TRACE] Application found route.")
            } else {
                println("Cache miss")
                val url = URL("https://$requestedPath")

                proxyPath.parent.createDirectories()
                // TODO: Use socket + connect + inputStream instead of encapsulated work of url.openStream()
                with(url.openStream()) {
                    Files.copy(this, proxyPath, StandardCopyOption.REPLACE_EXISTING)
                }
                println("Successfully create cache entry on server")
                val responseData = file.readBytes()

                output.write(OK_200.toByteArray())
                output.write(responseData)
                println("[TRACE] Application found route.")
            }
            println()
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

private fun InputStream.toFile(path: String): File {
    val file = File(path)
    file.outputStream().use { this.copyTo(it) }
    return file
}
