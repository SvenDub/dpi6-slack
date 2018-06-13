package nl.svendubbeld.fontys.slack.client

import nl.svendubbeld.fontys.slack.client.repository.LocalMessageRepository
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class MessagePersistenceListener(private var repository: LocalMessageRepository) {

    @EventListener
    fun onMessageReceived(event: MessageReceivedEvent) {
        repository.save(event.localMessage)
    }
}
