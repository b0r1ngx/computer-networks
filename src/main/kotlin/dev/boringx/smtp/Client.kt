package dev.boringx.smtp

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket
import java.util.Base64
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocket

/* Top-up info you may find helpful to AOL and Google services:

    AOL
    Server Address: smtp.aol.com
    Username: Your AOL Mail screen name (e.g. whatever comes before @aol.com)
    Password: Your AOL Mail password
    Gmail SMTP port: 465 (SMTP SSL) or 587 (SMTP TLS)
    Authentication: Required
    Sending Limits: 500 Emails a day or 100 connections a day.

    Google
    Server Address: smtp.gmail.com
    Username: your full Gmail address (xxxx@gmail.com)
    Password: your Gmail password
    Gmail SMTP port: 465 (SMTP SSL) or 587 (SMTP TLS)
    Authentication: Required
    Sending Limits: ???
 */

fun main() {
    googleRealisation()
}

fun googleRealisation() {
    val serverAddress = "smtp.gmail.com"
    val port = 587

    val recipient = "" // TODO: Set

    val sender = "" // TODO: Set
    val password = "" // TODO: Set

    // Connect to the server
    val clientSocket = Socket(serverAddress, port)
    val outputStream = OutputStreamWriter(clientSocket.getOutputStream())
    val inputStream = BufferedReader(InputStreamReader(clientSocket.getInputStream()))

    val firstResponseAfterEstablishConnectionWithGoogle = inputStream.readLine()
    println(firstResponseAfterEstablishConnectionWithGoogle) // Print server response

    if (!firstResponseAfterEstablishConnectionWithGoogle.startsWith("220")) {
        println("код 220 от сервера не получен.")
        return
    }

    // Send the HELO command (EHLO COMMAND DOESN'T WORK FOR GOOGLE SMTP !!!)
    outputStream.write("HELO $serverAddress\r\n")
    outputStream.flush()
    println("HELO response: ${inputStream.readLine()}") // Print server response

    // Send the STARTTLS command
    outputStream.write("STARTTLS\r\n")
    outputStream.flush()
    println("STARTTLS response: ${inputStream.readLine()}") // Print server response

    // Upgrade the connection to TLS
    // Initiate SSL/TLS context
    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(null, null, null)
    val sslSocketFactory = sslContext.socketFactory
    val tlsSocket = sslSocketFactory.createSocket(clientSocket, serverAddress, port, true) as SSLSocket
    tlsSocket.startHandshake()

    // Reconnect over TLS
    val tlsOutputStream = OutputStreamWriter(tlsSocket.getOutputStream())
    val tlsInputStream = BufferedReader(InputStreamReader(tlsSocket.getInputStream()))

    // Authenticate
    val authEncoded = Base64.getEncoder().encodeToString("$sender\u0000$sender\u0000$password".toByteArray())
    tlsOutputStream.write("AUTH PLAIN $authEncoded\r\n")
    tlsOutputStream.flush()
    println("AUTH PLAIN response: ${tlsInputStream.readLine()}")

    // Send the MAIL FROM command
    tlsOutputStream.write("MAIL FROM: <$sender>\r\n")
    tlsOutputStream.flush()
    println("MAIL FROM response: ${tlsInputStream.readLine()}")

    // Send the RCPT TO command
    tlsOutputStream.write("RCPT TO: <$recipient>\r\n")
    tlsOutputStream.flush()
    println("RCPT TO response: ${tlsInputStream.readLine()}")

    // Send the DATA command
    tlsOutputStream.write("DATA\r\n")
    tlsOutputStream.flush()
    println("DATA response: ${tlsInputStream.readLine()}")

    // Send the message content
    tlsOutputStream.write("Subject: Test Email\r\n")
    tlsOutputStream.write("\r\n")
    tlsOutputStream.write("Test message from Kotlin.\r\n")
    // Сообщение завершается одинарной точкой.
    tlsOutputStream.write(".\r\n")
    tlsOutputStream.flush()

    // Send the QUIT command
    tlsOutputStream.write("QUIT\r\n")
    tlsOutputStream.flush()
    println("QUIT response: ${tlsInputStream.readLine()}")

    clientSocket.close()
}
