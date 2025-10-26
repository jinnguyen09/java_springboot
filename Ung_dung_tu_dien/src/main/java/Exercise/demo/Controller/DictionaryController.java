package Exercise.demo.Controller;

import Exercise.demo.Service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @GetMapping("/")
    public String showSearchForm() {
        return "index";
    }

    @PostMapping("/search")
    public String searchWord(@RequestParam("englishWord") String word, Model model) {
        String meaning = dictionaryService.findMeaning(word);

        model.addAttribute("word", word);

        if (meaning != null) {
            model.addAttribute("meaning", meaning);
        } else {
            model.addAttribute("error", "Không tìm thấy từ này.");
        }
        return "result";
    }
}