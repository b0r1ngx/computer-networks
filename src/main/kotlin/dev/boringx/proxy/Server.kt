package dev.boringx.proxy

import RESOURCES_PATH
import SERVER_PORT
import dev.boringx.server.resources.values.OK_200
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import kotlin.io.path.createDirectories
import kotlin.io.path.Path
import kotlin.io.path.createDirectory
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
                val outputData = file.readBytes()

                output.write(OK_200.toByteArray())
                output.write(outputData)
                output.flush()
                println("[TRACE] Application found route.")
            } else {
                println("Cache miss")
                val socket = Socket()

                val prepareUrl = "https://$requestedPath"
                val url = URL(prepareUrl)
                val hostName = url.host
                println(hostName)
                try {
                    val hostIpAddress = InetAddress.getByName(hostName)
                    println(hostIpAddress.hostAddress)
                    // if http: 80, if https: 443
                    val socketAddress = InetSocketAddress(hostIpAddress, 443)
                    socket.connect(socketAddress)

//                    println("Creating a temporary file on this socket and request port 80 for the file that the client needs")
//                    val fileObj = OutputStreamWriter(socket.getOutputStream())
//                    fileObj.write("GET $prepareUrl HTTP/1.1\n\n")
//                    println("fileObj: $fileObj")

                    println("Read web-server response to buffer") // works so long
                    proxyPath.parent.createDirectories()
//                    val file = socket.getInputStream()
//                    print(file.readAllBytes().toString())
//                    file.toFile(proxyPath.pathString)

                    // TODO: Use socket + connect + inputStream instead of encapsulated work at url.openStream()
                    with(url.openStream()) {
                        Files.copy(this, proxyPath, StandardCopyOption.REPLACE_EXISTING)
                    }
//                    val bufferedReader = BufferedReader(InputStreamReader())
//                    val response = StringBuilder()
//                    var line: String? = bufferedReader.readLine()
//                    while (line != null) {
//                        response.append(line)
//                        line = bufferedReader.readLine()
//                    }

//                    println("Saves file on server")
//                    val tmpFile = FileOutputStream()
//                    tmpFile.write(response.toString().toByteArray())
//                    tmpFile.flush()
                    println("Successfully create cache entry on server")

                    output.write(file.readBytes())
                    output.flush()
                    println("[TRACE] Application found route.")
                } catch (e: Exception) {
                    println("Exception: ${e.stackTraceToString()}")
                } finally {
                    socket.close()
                    connectionSocket.close()
                }
            }
            println()
        } catch (e: Exception) {
            println("Exception: ${e.stackTraceToString()}")
        }
    }
}

private fun InputStream.toFile(path: String): File {
    val file = File(path)
    file.outputStream().use { this.copyTo(it) }
    return file
}
