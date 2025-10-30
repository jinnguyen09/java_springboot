package demo.Controller;

import demo.Model.Customer;
import demo.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping()
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("list");
        List<Customer> customers = customerService.findAll();
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView showInformation(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("info");
        Customer customers = customerService.findById(id);
        if (customers == null) {
            return new ModelAndView("redirect:/customers");
        }
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @PostMapping("/update")
    public String updateCustomer(Customer customer) {
        customerService.save(customer);
        return "redirect:/customers";
    }
}
