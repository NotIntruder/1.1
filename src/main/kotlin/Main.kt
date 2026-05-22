package main

import java.nio.charset.StandardCharsets
import java.io.FileInputStream
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel


fun main() {
    val messages = FileInputStream("messages.txt")
    val lines = getLinesChannel(messages)

    runBlocking {
        for (line in lines) {
            println("read: $line")
        }
    }
}

fun getLinesChannel(f: FileInputStream): Channel<String> {
    var stringHelper = ""
    val buffer = ByteArray(8)
    val channel = Channel<String>()

    GlobalScope.launch {
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
