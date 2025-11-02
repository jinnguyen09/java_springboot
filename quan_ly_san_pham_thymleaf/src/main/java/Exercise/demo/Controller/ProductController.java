package Exercise.demo.Controller;

import Exercise.demo.Model.Product;
import Exercise.demo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping({"", "/"})
    public String listProducts(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        if (keyword != null && !keyword.isEmpty()) {

            model.addAttribute("products", productService.searchProductsByName(keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("products", productService.getAllProducts());
        }
        return "list";
    }

    @GetMapping("/new")
    public String showNewProductForm(Model model) {
        // Khởi tạo Product rỗng để binding với form
        model.addAttribute("product", new Product());
        model.addAttribute("pageTitle", "Tạo Sản Phẩm Mới");
        return "form"; // Trả về form.html
    }

    @PostMapping("/save")
    public String saveProduct(Product product, RedirectAttributes ra) {
        productService.saveOrUpdateProduct(product);
        ra.addFlashAttribute("message", "Lưu sản phẩm thành công!");
        return "redirect:/products";
    }


    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            Product product = productService.getProductById(id).orElseThrow(() -> new IllegalArgumentException("ID Sản phẩm không hợp lệ:" + id));
            model.addAttribute("product", product);
            model.addAttribute("pageTitle", "Cập Nhật Sản Phẩm (ID: " + id + ")");
            return "form"; // Trả về form.html (tái sử dụng)
        } catch (Exception e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/products";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            productService.deleteProduct(id);
            ra.addFlashAttribute("message", "Xoá sản phẩm ID " + id + " thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("message", "Lỗi: Không thể xoá sản phẩm ID " + id);
        }
        return "redirect:/products";
    }

    @GetMapping("/details/{id}")
    public String viewProductDetails(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            Product product = productService.getProductById(id).orElseThrow(() -> new IllegalArgumentException("ID Sản phẩm không hợp lệ:" + id));
            model.addAttribute("product", product);
            return "details";
        } catch (Exception e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/products";
        }
    }
}