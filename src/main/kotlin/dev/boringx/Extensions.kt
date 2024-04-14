package dev.boringx

import dev.boringx.resources.values.FileNotFound
import dev.boringx.resources.values.NOT_FOUND_404
import dev.boringx.resources.values.OK_200
import java.io.OutputStream
import java.lang.Exception

fun OutputStream.sendResponse200(data: ByteArray) {
    write(OK_200.toByteArray())
    write(data)
    println("[TRACE] Application found route.")
}

fun OutputStream.sendResponse404(exception: Exception) {
    write(NOT_FOUND_404.toByteArray())
    write(FileNotFound.toByteArray())
    println("[TRACE] Application not found route. Exception: ${exception.stackTraceToString()}")
}
