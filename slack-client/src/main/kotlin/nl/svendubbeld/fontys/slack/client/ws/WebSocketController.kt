package nl.svendubbeld.fontys.slack.client.ws

import nl.svendubbeld.fontys.slack.client.MessageReceivedEvent
import nl.svendubbeld.fontys.slack.client.amqp.Sender
import nl.svendubbeld.fontys.slack.shared.Message
import org.springframework.context.event.EventListener
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller

@Controller
class WebSocketController(val template: SimpMessagingTemplate, val sender: Sender) {

    @MessageMapping("/send")
    fun sendMessage(message: Message, @Header("X-Destination") destination: String) {
        sender.sendMessage(message, destination)
    }

    @EventListener
    fun onMessageReceived(event: MessageReceivedEvent) {
        println("Received <${event.message.message}>")

        template.convertAndSend("/topic/messages", event.message)
    }
}
