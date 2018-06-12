package nl.svendubbeld.fontys.slack.client.ws

import nl.svendubbeld.fontys.slack.client.*
import nl.svendubbeld.fontys.slack.shared.Message
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import java.time.OffsetDateTime

@Controller
class WebSocketController(val template: SimpMessagingTemplate, val messageService: MessageService, val slackConfiguration: SlackConfiguration) {

    private val logger = LoggerFactory.getLogger(WebSocketController::class.java)

    @MessageMapping("/send")
    fun sendMessage(message: Message, @Header("X-Destination") destination: String) {
        message.date = OffsetDateTime.now()
        message.sender = slackConfiguration.user
        message.destination = destination
        messageService.sendMessage(message, destination)
    }

    @EventListener
    fun onMessageReceived(event: MessageReceivedEvent) {
        logger.info("Received <${event.message.content}>")

        template.convertAndSend("/topic/messages", LocalMessage(event.message, if (event.message.sender == slackConfiguration.user) MessageType.SENT else MessageType.RECEIVED))
    }
}
