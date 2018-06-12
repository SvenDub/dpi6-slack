package nl.svendubbeld.fontys.slack.server.processor

import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.core.annotation.Order

@Order(MESSAGE_ORDER_LOG)
@MessageProcessor
class LogProcessor(private val messageConverter: MessageConverter) : Processor<Message, Message> {
    private val logger = LoggerFactory.getLogger(LogProcessor::class.java)

    override fun process(item: Message): Message {
        val message = messageConverter.fromMessage(item) as nl.svendubbeld.fontys.slack.shared.Message
        logger.info("${message.sender} => ${message.destination}: ${message.content}")

        return item
    }
}
