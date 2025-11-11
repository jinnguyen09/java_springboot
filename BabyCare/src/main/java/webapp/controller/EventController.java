package webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventController {
    @GetMapping("/event")
    public String event(Model model) {
        model.addAttribute("activePage", "event");
        return "event";
    }
}
