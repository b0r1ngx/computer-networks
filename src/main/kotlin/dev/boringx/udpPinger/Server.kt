package dev.boringx.udpPinger

import SERVER_PORT
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.util.Locale
import kotlin.random.Random

fun main() {
    val serverSocket = DatagramSocket(SERVER_PORT)
    val receiveData = ByteArray(1024)
    println("[INFO] Application - Responding at http://${serverSocket.localSocketAddress}")
    while (true) {
        val requestDatagramPacket = DatagramPacket(receiveData, receiveData.size)
        serverSocket.receive(requestDatagramPacket)
        val message = String(bytes = requestDatagramPacket.data, offset = 0, length = requestDatagramPacket.length)

        // Simulate packet loss by randomly dropping packets (drop 4/10 of packets)
        if (Random.nextInt(0, 11) < 4) {
            println("[INFO] Packet lost")
            continue
        }

        val address = requestDatagramPacket.address
        val port = requestDatagramPacket.port
        val responseByteArray = message.uppercase(Locale.getDefault()).toByteArray()
        val sendPacket = DatagramPacket(responseByteArray, responseByteArray.size, address, port)
        serverSocket.send(sendPacket)
        println("[INFO] Successfully response on request")
    }
}