package nl.svendubbeld.fontys.slack.client.ws

import nl.svendubbeld.fontys.slack.client.LocalMessage
import nl.svendubbeld.fontys.slack.client.MessageReceivedEvent
import nl.svendubbeld.fontys.slack.client.MessageService
import nl.svendubbeld.fontys.slack.client.MessageType
import nl.svendubbeld.fontys.slack.shared.Message
import org.springframework.context.event.EventListener
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import java.time.OffsetDateTime

@Controller
class WebSocketController(val template: SimpMessagingTemplate, val messageService: MessageService) {

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    fun sendMessage(message: Message, @Header("X-Destination") destination: String): LocalMessage {
        message.date = OffsetDateTime.now()
        messageService.sendMessage(message, destination)

        return LocalMessage(message, MessageType.SENT)
    }

    @EventListener
    fun onMessageReceived(event: MessageReceivedEvent) {
        println("Received <${event.message.content}>")

        template.convertAndSend("/topic/messages", LocalMessage(event.message, MessageType.RECEIVED))
    }
}
