package nl.svendubbeld.fontys.slack.client.web

import nl.svendubbeld.fontys.slack.client.MessageService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class IndexController(val messageService: MessageService) {

    @GetMapping
    fun getIndex(model: Model): String {
        model.addAttribute("messages", messageService.messages)
        return "index"
    }
}
