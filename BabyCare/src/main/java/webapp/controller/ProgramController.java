package webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProgramController {
    @GetMapping("/program")
    public String program(Model model) {
        model.addAttribute("activePage", "program");
        return "program";
    }
}
