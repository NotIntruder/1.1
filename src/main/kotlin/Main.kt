package main

import java.nio.charset.StandardCharsets
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.io.InputStream
import java.net.ServerSocket
import java.net.Socket


fun main() = runBlocking {
    val source = ServerSocket(6767)
    while (true) {
        // Now the implementation launches new instances in parallel per connection making it One-to-Many relation
        val socket = source.accept()
        launch {
            sessionHandler(socket)
        }
    }
//        println("Connection closed.")
//        source.close();
}

fun CoroutineScope.getLinesChannel(f: InputStream): Channel<String> {
    var stringHelper = ""
    val buffer = ByteArray(8)
    val channel = Channel<String>()

    launch {
        while (true) {
            val read = f.read(buffer)

            if (read == -1) {
                break
            }
            //println("read: ${String(buffer, 0, read)}")
            /*Get line data till "/n"
            then print the entire line
            then we repeat
            */
            stringHelper += String(buffer, 0, read, StandardCharsets.UTF_8)

            while (stringHelper.contains('\n')) {
                val indexOf = stringHelper.indexOf('\n')
                channel.send(stringHelper.substring(0, indexOf))
                stringHelper = stringHelper.substring(indexOf + 1)
            }

        }

        if (stringHelper.isNotEmpty()) {
            channel.send(stringHelper)
        }

        channel.close()
        f.close()
    }
    return channel
}

fun CoroutineScope.sessionHandler(socket: Socket) {
    println("Connection accepted.")
    val lines = getLinesChannel(socket.getInputStream())

    for (line in lines) {
        println(line)
    }

    println("Connection closed.")
    socket.close()
}
