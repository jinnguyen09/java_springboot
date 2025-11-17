package demo.service;

import demo.model.Promotion;
import demo.repository.InMemoryPromotionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionService {

    private final InMemoryPromotionRepository repo;

    public PromotionService(InMemoryPromotionRepository repo) {
        this.repo = repo;
    }

    public Promotion save(Promotion p) {
        return repo.save(p);
    }

    public List<Promotion> findAll() {
        return repo.findAll();
    }

    public Optional<Promotion> findById(Long id) {
        return repo.findById(id);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public List<Promotion> search(Integer discount, LocalDate startDate, LocalDate endDate) {
        return findAll().stream()

                .filter(p -> discount == null
                        || (p.getDiscountAmount() != null
                        && p.getDiscountAmount() == discount.longValue()))

                .filter(p -> startDate == null
                        || (p.getStartDate() != null && !p.getStartDate().isBefore(startDate)))

                .filter(p -> endDate == null
                        || (p.getEndDate() != null && !p.getEndDate().isAfter(endDate)))

                .toList();
    }
}
