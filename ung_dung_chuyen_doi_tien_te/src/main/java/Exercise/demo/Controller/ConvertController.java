package Exercise.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // Dùng @Controller (không phải @RestController) vì chúng ta trả về HTML
public class ConvertController {

    /**
     * Hàm này xử lý khi người dùng truy cập trang chủ (GET /)
     * Nó chỉ đơn giản là trả về file 'index.html'
     */
    @GetMapping("/")
    public String showForm() {
        // Trả về tên của file HTML trong 'templates' (không cần .html)
        return "index";
    }

    /**
     * Hàm này xử lý khi người dùng bấm nút "Chuyển đổi" (POST /convert)
     * Nó nhận 2 tham số từ form
     */
    @PostMapping("/convert")
    public String convert(
            @RequestParam("rate") double rate, // Lấy giá trị từ input 'rate'
            @RequestParam("usd") double usd,   // Lấy giá trị từ input 'usd'
            Model model) {                     // 'Model' để gửi dữ liệu sang trang HTML

        // 1. Tính toán
        double vnd = rate * usd;

        // 2. Gửi dữ liệu qua trang kết quả
        model.addAttribute("usdAmount", usd);
        model.addAttribute("rateAmount", rate);
        model.addAttribute("vndResult", vnd);

        // 3. Trả về file 'result.html'
        return "result";
    }
}