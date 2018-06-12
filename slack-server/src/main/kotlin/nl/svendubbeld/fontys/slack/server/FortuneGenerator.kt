package nl.svendubbeld.fontys.slack.server

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.*
import java.util.concurrent.TimeUnit

@Component
class FortuneGenerator {

    private val logger = LoggerFactory.getLogger(FortuneGenerator::class.java)

    fun getFortune(): String {
        try {
            val fortune = ProcessBuilder()
                    .command("fortune")
                    .start()

            if (fortune.waitFor(1, TimeUnit.SECONDS) && fortune.exitValue() == 0) {
                BufferedReader(InputStreamReader(fortune.inputStream)).use {
                    return it.readText()
                }
            } else {
                fortune.destroy()
            }
        } catch (e: IOException) {
            logger.error("fortune not supported", e)
        }

        return "fortune not supported"
    }

    fun getCowFortune(): String {
        try {
        val cowsay = ProcessBuilder()
                .command("cowsay")
                .redirectInput(ProcessBuilder.Redirect.PIPE)
                .start()

        BufferedWriter(OutputStreamWriter(cowsay.outputStream)).use {
            it.write(getFortune())
        }

        if (cowsay.waitFor(1, TimeUnit.SECONDS) && cowsay.exitValue() == 0) {
            BufferedReader(InputStreamReader(cowsay.inputStream)).use {
                return it.readText()
            }
        } else {
            cowsay.destroy()
        }
        } catch (e: IOException) {
            logger.error("cowsay not supported", e)
        }

        return "cowsay not supported"
    }

}
