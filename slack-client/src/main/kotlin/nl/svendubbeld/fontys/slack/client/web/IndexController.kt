package nl.svendubbeld.fontys.slack.client.web

import nl.svendubbeld.fontys.slack.client.SlackConfiguration
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class IndexController(private val slackConfiguration: SlackConfiguration) {

    @GetMapping
    fun getIndex(model: Model): String {
        model.addAttribute("user", slackConfiguration.user)
        return "index"
    }
}
