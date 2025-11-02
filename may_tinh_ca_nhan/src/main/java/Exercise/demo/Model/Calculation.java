package Exercise.demo.Model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Calculation {
    // Getters và Setters
    private double number1;
    private double number2;
    private String operation; // Lưu phép toán: add, subtract, multiply, divide
    private double result;
    private String errorMessage; // Để xử lý lỗi (ví dụ: chia cho 0)

    // Constructor mặc định
    public Calculation() {}

}