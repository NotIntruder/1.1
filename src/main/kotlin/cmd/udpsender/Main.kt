package cmd.udpsender


import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

fun main() {
    val address = InetAddress.getByName("localhost")
    DatagramSocket().use { socket ->
        val reads = System.`in`.bufferedReader()

        while (true) {
            print(">")
            val lines = reads.readLine() ?: break
            val pipeline = lines.toByteArray()
            val packet = DatagramPacket(pipeline, pipeline.size, address, 6767)
            socket.send(packet)
        }
    }
}



