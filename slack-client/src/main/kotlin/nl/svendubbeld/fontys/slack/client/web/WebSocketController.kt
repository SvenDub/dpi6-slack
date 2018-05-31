package nl.svendubbeld.fontys.slack.client.web

import nl.svendubbeld.fontys.slack.client.MessageReceivedEvent
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller

@Controller
class WebSocketController(val template: SimpMessagingTemplate) {

    @EventListener
    fun onMessageReceived(event: MessageReceivedEvent) {
        println("Received <${event.message.message}>")

        template.convertAndSend("/topic/messages", event.message)
    }
}
