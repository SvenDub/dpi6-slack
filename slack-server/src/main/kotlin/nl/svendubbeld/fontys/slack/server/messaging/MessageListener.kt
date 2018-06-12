package nl.svendubbeld.fontys.slack.server.messaging

import nl.svendubbeld.fontys.slack.server.processor.MESSAGE_ORDER_DEFAULT
import nl.svendubbeld.fontys.slack.server.processor.MessageProcessor
import nl.svendubbeld.fontys.slack.server.processor.Processor
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageListener
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
class MessageListener(private val messageRouter: MessageRouter, @MessageProcessor private val processors: List<Processor<Message, Message>>) : MessageListener {

    private val logger = LoggerFactory.getLogger(MessageListener::class.java)

    @Transactional
    override fun onMessage(message: Message) {
        var processedMessage: Message? = null

        processors
                .filter { it::class.java.isAnnotationPresent(MessageProcessor::class.java) }
                .sortedByDescending { it::class.java.getAnnotation(Order::class.java)?.value ?: MESSAGE_ORDER_DEFAULT}
                .forEach { processedMessage = it.process(processedMessage ?: message) }

        messageRouter.routeMessage(processedMessage ?: message)
    }
}
