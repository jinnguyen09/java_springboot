package Exercise.demo.Controller;

import Exercise.demo.Model.Condiment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SandwichController {

    private static final String[] ALL_CONDIMENTS = {"Lettuce", "Tomato", "Mustard", "Sprouts", "Mayonnaise", "Ketchup"};

    @GetMapping("/select")
    public String showSelectionForm(Model model) {
        model.addAttribute("condiment", new Condiment());

        model.addAttribute("allCondiments", ALL_CONDIMENTS);

        return "form";
    }

    @PostMapping("/save")
    public ModelAndView saveCondiments(@ModelAttribute("condiment") Condiment condiment) {

        ModelAndView modelAndView = new ModelAndView("result");
        modelAndView.addObject("condiment", condiment);
        return modelAndView;
    }
}