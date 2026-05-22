package main
import java.io.FileInputStream;

fun main () {

    val messages = FileInputStream("messages.txt")
    val buffer = ByteArray(8)
    var printHelper = ""

    while (true) {
        val read = messages.read(buffer)

        if (read == -1) {
            break
        }
        //println("read: ${String(buffer, 0, read)}")
        /*Get line data till "/n";
        then print the entire line
        then we repeat
        */
        printHelper += String(buffer, 0, read)

        while (printHelper.contains('\n')) {
            var indexOf = printHelper.indexOf('\n')
            println("read: ${printHelper.substring(0, indexOf)}")
            printHelper = printHelper.substring(indexOf + 1)
        }

    }

    if (printHelper.isNotEmpty()) {
        println("read: $printHelper")
    }

        messages.close()
}