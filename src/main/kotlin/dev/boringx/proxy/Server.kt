package dev.boringx.proxy

import RESOURCES_PATH
import SERVER_PORT
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import java.net.URL

private const val CACHE = "$RESOURCES_PATH/cache/"

fun main() {
    val serverSocket = ServerSocket(SERVER_PORT)

    println("[INFO] Application - Responding at http://${serverSocket.localSocketAddress}")
    while (true) {
        val connectionSocket = serverSocket.accept()
        val output = connectionSocket.getOutputStream()
        try {
            val input = connectionSocket.inputStream.bufferedReader()
            val request = input.readLine().also { println(it) }

            // Извлекаем имя файла из сообщения
            val requestedPath = request.split(" ")[1].removePrefix("/")
            println("requestedPath: $requestedPath")

            val file = File(CACHE + requestedPath)
            if (file.exists()) {
                println("Cache hit")
                val outputData = file.readBytes()

                output.write("HTTP/1.0 200 OK\r\n".toByteArray())
                output.write("Content-Type:text/html\r\n".toByteArray())
                output.write(outputData)
                output.flush()
            } else {
                println("Cache miss")
                val socket = Socket()

                val prepareUrl = "https://$requestedPath"
                val url = URL(prepareUrl)
                val hostName = url.host.also { println(it) }
                try {
                    val hostIpAddress = InetAddress.getByName(hostName)
                    println(hostIpAddress.hostAddress)
                    // if https: 443
                    val socketAddress = InetSocketAddress(hostIpAddress, 443)
                    socket.connect(socketAddress)
                    println("socket connected")

                    println("Создаем временный файл на этом сокете и запрашиваем порт 80 файл, который нужен клиенту")
                    val fileobj = OutputStreamWriter(socket.getOutputStream())
                    fileobj.write("GET $prepareUrl HTTP/1.1\n\n")

                    println("Читаем ответ в буфер") // works so long
                    val bufferedReader = BufferedReader(InputStreamReader(socket.getInputStream()))
                    val response = StringBuilder()
                    var line: String? = bufferedReader.readLine()
                    while (line != null) {
                        response.append(line)
                        line = bufferedReader.readLine()
                    }

                    println("Saves file on server")
                    val fileName = requestedPath.split("/").last()
                    val tmpFile = FileOutputStream(RESOURCES_PATH + "/cache/" + fileName)
                    tmpFile.write(response.toString().toByteArray())
                    tmpFile.flush()
                    println("Successfully create cache-entry on server")

                    output.write(response.toString().toByteArray())
                    output.flush()
                } catch (e: Exception) {
                    println("Exception: ${e.stackTraceToString()}")
                } finally {
                    socket.close()
                    connectionSocket.close()
                }
            }
        } catch (e: Exception) {
            println("Exception: ${e.stackTraceToString()}")
        }
    }
}
