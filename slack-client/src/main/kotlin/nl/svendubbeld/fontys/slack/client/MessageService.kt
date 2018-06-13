package nl.svendubbeld.fontys.slack.client

import nl.svendubbeld.fontys.slack.client.amqp.Sender
import nl.svendubbeld.fontys.slack.shared.Message
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class MessageService(val sender: Sender, val applicationEventPublisher: ApplicationEventPublisher, val slackConfiguration: SlackConfiguration) {

    fun onReceiveMessage(message: Message) {
        applicationEventPublisher.publishEvent(MessageReceivedEvent(LocalMessage(message, if (message.sender == slackConfiguration.user) MessageType.SENT else MessageType.RECEIVED)))
    }

    fun sendMessage(message: Message, destination: String) {
        sender.sendMessage(message, destination)
    }

}
