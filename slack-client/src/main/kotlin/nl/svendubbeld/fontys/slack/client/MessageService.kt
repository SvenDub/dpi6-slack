package nl.svendubbeld.fontys.slack.client

import nl.svendubbeld.fontys.slack.client.amqp.Sender
import nl.svendubbeld.fontys.slack.shared.Message
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class MessageService(val sender: Sender, val applicationEventPublisher: ApplicationEventPublisher) {

    fun onReceiveMessage(message: Message) {
        applicationEventPublisher.publishEvent(MessageReceivedEvent(message))
    }

    fun sendMessage(message: Message, destination: String) {
        sender.sendMessage(message, destination)
    }

}
