package nl.svendubbeld.fontys.slack.client.amqp

import nl.svendubbeld.fontys.slack.client.MessageService
import nl.svendubbeld.fontys.slack.shared.Message
import org.springframework.stereotype.Component

@Component
class Receiver(val messageService: MessageService) {

    fun receiveMessage(message: Message) {
        messageService.onReceiveMessage(message)
    }
}
