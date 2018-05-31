package nl.svendubbeld.fontys.slack.client

import nl.svendubbeld.fontys.slack.shared.Message
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class MessageService(val applicationEventPublisher: ApplicationEventPublisher) {

    private val _messages: MutableList<Message> = mutableListOf()
    val messages: List<Message> get() = _messages.toList()

    fun addMessage(message: Message) {
        _messages.add(message)
        applicationEventPublisher.publishEvent(MessageReceivedEvent(message))
    }

}
