package Exercise.demo.Controller;

import Exercise.demo.Model.Customer;
import Exercise.demo.Service.CustomerService;
import Exercise.demo.Service.ICustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/customers")

public class CustomerController {

    private final ICustomerService customerService = new CustomerService();

    @GetMapping("")

    public String index(Model model) {

        List<Customer> customerList = customerService.findAll();

        model.addAttribute("customers", customerList);

        return "/index";

    }

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("customer", new Customer());

        return "/create";

    }

    @PostMapping("/save")
    public String save(Customer customer, RedirectAttributes redirect) {
        customer.setId((int) (Math.random() * 10000));
        customerService.save(customer);
        redirect.addFlashAttribute("success", "Added customer successfully!");
        return "redirect:/customers";
    }

    @GetMapping("/{id}/edit")
    public String update(@PathVariable int id, Model model) {

        model.addAttribute("customer", customerService.findById(id));

        return "/update";

    }

    @PostMapping("/update")
    public String update(Customer customer, RedirectAttributes redirect) {
        customerService.update(customer.getId(), customer);
        redirect.addFlashAttribute("success", "Updated customer successfully!");
        return "redirect:/customers";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, Model model) {

        model.addAttribute("customer", customerService.findById(id));

        return "/delete";

    }

    @GetMapping("/{id}/view")
    public String view(@PathVariable int id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "/view";
    }
}
