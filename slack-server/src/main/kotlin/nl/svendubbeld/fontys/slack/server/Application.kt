package nl.svendubbeld.fontys.slack.server

import nl.svendubbeld.fontys.slack.shared.RECEIVE_QUEUE
import nl.svendubbeld.fontys.slack.shared.SEND_QUEUE
import nl.svendubbeld.fontys.slack.shared.TOPIC_EXCHANGE
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {

    @Bean
    fun sendQueue(): Queue {
        return Queue(SEND_QUEUE)
    }

    @Bean
    fun receiveQueue(): Queue {
        return Queue(RECEIVE_QUEUE)
    }

    @Bean
    fun exchange(): TopicExchange {
        return TopicExchange(TOPIC_EXCHANGE)
    }

    @Bean
    fun binding(sendQueue: Queue, exchange: TopicExchange): Binding {
        return BindingBuilder.bind(sendQueue).to(exchange).with("*.*")
    }

    @Bean
    fun container(connectionFactory: ConnectionFactory, listenerAdapter: MessageListenerAdapter): SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueueNames(SEND_QUEUE)
        container.setMessageListener(listenerAdapter)
        return container
    }

    @Bean
    fun listenerAdapter(receiver: Receiver): MessageListenerAdapter {
        return MessageListenerAdapter(receiver, "receiveMessage")
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}