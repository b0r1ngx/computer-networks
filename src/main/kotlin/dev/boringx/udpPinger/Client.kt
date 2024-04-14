package dev.boringx.udpPinger

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

fun main() {
    val clientSocket = DatagramSocket()
    val serverIp = byteArrayOf(192.toByte(), 168.toByte(), 0, 104)
    val serverAddress = InetAddress.getByAddress(serverIp)

    repeat(10) { sequence ->
        val message = "Ping ${sequence + 1} ${System.currentTimeMillis()}"
        val sendData = message.toByteArray()
        val sendPacket = DatagramPacket(sendData, sendData.size, serverAddress, 8080)

        val startTime = System.currentTimeMillis()

        clientSocket.send(sendPacket)

        try {
            val receiveData = ByteArray(1024)
            val receivePacket = DatagramPacket(receiveData, receiveData.size)

            clientSocket.soTimeout = 1000 // Set timeout to 1 second

            clientSocket.receive(receivePacket)
            val endTime = System.currentTimeMillis()

            val responseMessage = String(receivePacket.data, 0, receivePacket.length)
            println("Response: $responseMessage")
            println("RTT: ${(endTime - startTime) / 1000.0} seconds")
        } catch (e: Exception) {
            println("Request timed out")
        }
    }

    clientSocket.close()
}
