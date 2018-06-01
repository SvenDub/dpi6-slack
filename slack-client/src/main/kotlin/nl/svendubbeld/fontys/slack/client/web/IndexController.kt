package nl.svendubbeld.fontys.slack.client.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class IndexController {

    @GetMapping
    fun getIndex(model: Model): String {
        return "index"
    }
}
