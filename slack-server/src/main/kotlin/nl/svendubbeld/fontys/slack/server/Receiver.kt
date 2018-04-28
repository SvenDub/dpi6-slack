package nl.svendubbeld.fontys.slack.server

import nl.svendubbeld.fontys.slack.shared.Message
import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch

@Component
class Receiver {

    val latch = CountDownLatch(1)

    fun receiveMessage(message: Message) {
        println("Received <${message.message}>")
        latch.countDown()
    }
}