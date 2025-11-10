package webapp.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventController {
    @GetMapping("/event")
    public String event(Model model) {
        return "event";
    }
}
