package webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServiceController {
    @GetMapping("/service")
    public String service(Model model) {
        model.addAttribute("activePage", "service");
        return "service";
    }
}
