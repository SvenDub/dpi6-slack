package nl.svendubbeld.fontys.slack.server.processor

interface Processor<T, U> {

    fun process(item: T): U
}
