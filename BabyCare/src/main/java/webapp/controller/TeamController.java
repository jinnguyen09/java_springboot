package webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TeamController {
    @GetMapping("/team")
    public String team(Model model) {
        model.addAttribute("activePage", "pages");
        return "team";
    }
}
