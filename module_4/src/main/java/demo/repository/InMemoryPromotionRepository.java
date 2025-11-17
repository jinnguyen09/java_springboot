package demo.repository;

import demo.model.Promotion;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryPromotionRepository {
    private final Map<Long, Promotion> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(0);

    public InMemoryPromotionRepository() {
        save(make("Giảm giá Tết 50%",
                LocalDate.of(2025, 1, 10),
                LocalDate.of(2025, 1, 20),
                50000L,
                "Khuyến mãi lớn dịp Tết Nguyên Đán"));

        save(make("Sale Valentine 30%",
                LocalDate.of(2025, 2, 10),
                LocalDate.of(2025, 2, 15),
                30000L,
                "Ưu đãi đặc biệt mùa Valentine"));

        save(make("Black Friday giảm sốc",
                LocalDate.of(2025, 11, 20),
                LocalDate.of(2025, 11, 30),
                80000L,
                "Khuyến mãi siêu sốc Black Friday"));

        save(make("Summer Sale 20%",
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 10),
                20000L,
                "Giảm giá mùa hè cực hấp dẫn"));

        save(make("Giảm giá cuối năm",
                LocalDate.of(2025, 12, 20),
                LocalDate.of(2025, 12, 31),
                90000L,
                "Ưu đãi lớn dịp cuối năm"));
    }

    private Promotion make(String title, LocalDate start, LocalDate end, Long discount, String details) {
        Promotion p = new Promotion();
        p.setTitle(title);
        p.setStartDate(start);
        p.setEndDate(end);
        p.setDiscountAmount(discount);
        p.setDetails(details);
        return p;
    }

    public Promotion save(Promotion p) {
        if (p.getId() == null) {
            p.setId(idGen.incrementAndGet());
        }
        store.put(p.getId(), p);
        return p;
    }

    public Optional<Promotion> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Promotion> findAll() {
        List<Promotion> list = new ArrayList<>(store.values());
        list.sort(Comparator.comparingLong(Promotion::getId));
        return list;
    }

    public void deleteById(Long id) {
        store.remove(id);
    }
}
