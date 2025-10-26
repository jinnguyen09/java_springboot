package Exercise.demo.Controller;

import Exercise.demo.Model.Customer;
import Exercise.demo.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public String showCustomerList(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);
        return "edit";
    }


    @PostMapping("/update")
    public String updateCustomer(@ModelAttribute Customer customer) {
        customerService.save(customer);
        return "redirect:/";
    }
}