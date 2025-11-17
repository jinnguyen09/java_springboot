package demo.controller;

import demo.dto.PromotionForm;
import demo.model.Promotion;
import demo.service.PromotionService;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/promotions")
public class PromotionController {

    private final PromotionService service;

    public PromotionController(PromotionService service) {
        this.service = service;
    }

//    @GetMapping("home")
//    public String list(Model model) {
//        model.addAttribute("promotions", service.findAll());
//        return "promotions/home";
//    }

    @GetMapping("/add")
    public String addForm(Model model) {

        if (!model.containsAttribute("promotionForm")) {
            model.addAttribute("promotionForm", new PromotionForm());
        }

        return "promotions/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("promotionForm") PromotionForm form,
                       Model model) {

        Map<String, String> errors = new HashMap<>();

        if (form.getTitle() == null || form.getTitle().trim().isEmpty()) {
            errors.put("title", "Tiêu đề là bắt buộc");
        }

        if (form.getStartDate() == null) {
            errors.put("startDate", "Thời gian bắt đầu là bắt buộc");
        }

        if (form.getEndDate() == null) {
            errors.put("endDate", "Thời gian kết thúc là bắt buộc");
        }

        if (form.getDiscountAmount() == null) {
            errors.put("discountAmount", "Mức giảm giá là bắt buộc");
        } else if (form.getDiscountAmount() <= 10000) {
            errors.put("discountAmount", "Mức giảm giá phải lớn hơn 10.000 VNĐ");
        }

        if (form.getStartDate() != null) {
            if (!form.getStartDate().isAfter(LocalDate.now())) {
                errors.put("startDate", "Thời gian bắt đầu phải lớn hơn thời gian hiện tại");
            }
        }

        if (form.getStartDate() != null && form.getEndDate() != null) {
            if (!form.getEndDate().isAfter(form.getStartDate())) {
                errors.put("endDate", "Thời gian kết thúc phải lớn hơn thời gian bắt đầu ít nhất 1 ngày");
            } else if (form.getStartDate().plusDays(1).isAfter(form.getEndDate())) {
                errors.put("endDate", "Thời gian kết thúc phải lớn hơn thời gian bắt đầu ít nhất 1 ngày");
            }
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "promotions/form";
        }

        Promotion p = new Promotion();
        BeanUtils.copyProperties(form, p);
        service.save(p);

        return "redirect:/promotions/home";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<Promotion> opt = service.findById(id);
        if (opt.isEmpty()) {
            return "redirect:/promotions/home";
        }

        Promotion p = opt.get();
        PromotionForm form = new PromotionForm();
        BeanUtils.copyProperties(p, form);

        model.addAttribute("promotionForm", form);
        return "promotions/form";
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/promotions/home";
    }

    @GetMapping({"", "/", "home"})
    public String list(
            @RequestParam(required = false) Integer discount,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {

        model.addAttribute("promotions", service.search(discount, startDate, endDate));
        model.addAttribute("searchDiscount", discount);
        model.addAttribute("searchStartDate", startDate);
        model.addAttribute("searchEndDate", endDate);

        return "promotions/home";
    }


}
