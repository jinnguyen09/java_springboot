package Exercise.demo.Controller;

import Exercise.demo.Model.Calculation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalculatorController {

    /**
     * 1. Hiển thị form máy tính và khởi tạo kết quả mặc định
     */
    @GetMapping("/")
    public String showCalculator(Model model) {
        // Khởi tạo đối tượng Calculation mặc định (12 / 12 = 1)
        Calculation defaultCalc = new Calculation();
        defaultCalc.setNumber1(12);
        defaultCalc.setNumber2(12);
        defaultCalc.setOperation("Division");
        defaultCalc.setResult(1.0); // 12/12 = 1

        model.addAttribute("calculation", defaultCalc);
        return "calculator";
    }

    /**
     * 2. Xử lý yêu cầu tính toán từ các nút Addition, Subtraction, v.v.
     * Sử dụng @RequestParam để lấy dữ liệu từ form khi submit.
     */
    @PostMapping("/calculate")
    public String calculate(@RequestParam("number1") double number1,
                            @RequestParam("number2") double number2,
                            @RequestParam("operation") String operation,
                            Model model) {

        Calculation calc = new Calculation();
        calc.setNumber1(number1);
        calc.setNumber2(number2);
        calc.setOperation(operation);

        String operationName = getOperationName(operation);

        try {
            double result = performCalculation(number1, number2, operation);
            calc.setResult(result);
        } catch (ArithmeticException e) {
            calc.setErrorMessage(e.getMessage());
        }

        // Truyền lại đối tượng Calculation đã có kết quả/lỗi cho View
        model.addAttribute("calculation", calc);
        model.addAttribute("operationName", operationName);

        // Trả về cùng một view (calculator.html) để hiển thị kết quả
        return "calculator";
    }

    /**
     * Logic thực hiện phép tính
     */
    private double performCalculation(double num1, double num2, String operation) {
        return switch (operation) {
            case "add" -> num1 + num2;
            case "subtract" -> num1 - num2;
            case "multiply" -> num1 * num2;
            case "divide" -> {
                if (num2 == 0) {
                    throw new ArithmeticException("Không thể chia cho 0.");
                }
                yield num1 / num2;
            }
            default -> throw new IllegalArgumentException("Phép toán không hợp lệ.");
        };
    }

    /**
     * Chuyển đổi mã phép toán sang tên hiển thị
     */
    private String getOperationName(String operation) {
        return switch (operation) {
            case "add" -> "Addition";
            case "subtract" -> "Subtraction";
            case "multiply" -> "Multiplication";
            case "divide" -> "Division";
            default -> "";
        };
    }
}