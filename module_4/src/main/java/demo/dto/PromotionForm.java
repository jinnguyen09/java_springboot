package demo.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PromotionForm {
    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long discountAmount;
    private String details;
}
