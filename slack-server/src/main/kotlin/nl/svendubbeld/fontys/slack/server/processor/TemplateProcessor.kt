package nl.svendubbeld.fontys.slack.server.processor

import nl.svendubbeld.fontys.slack.server.FortuneGenerator
import org.springframework.amqp.core.Message
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.core.annotation.Order

@Order(MESSAGE_ORDER_TEMPLATE)
@MessageProcessor
class TemplateProcessor(private val messageConverter: MessageConverter, private val fortuneGenerator: FortuneGenerator) : Processor<Message, Message> {

    val templates: Map<String, () -> String> = mapOf(
            ":smile:" to {"\uD83D\uDE00"},
            ":sad:" to {"\uD83D\uDE1E"},
            ":fortune:" to {fortuneGenerator.getFortune()},
            ":cowfortune:" to {fortuneGenerator.getCowFortune()}
    )

    override fun process(item: Message): Message {
        val message = messageConverter.fromMessage(item) as nl.svendubbeld.fontys.slack.shared.Message

        templates.forEach { template, replacement -> message.content = message.content.replace(template, replacement.invoke()) }

        return messageConverter.toMessage(message, item.messageProperties)
    }

}
