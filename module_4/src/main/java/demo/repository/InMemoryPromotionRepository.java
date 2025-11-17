package demo.repository;

import demo.model.Promotion;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryPromotionRepository {
    private final Map<Long, Promotion> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(0);

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
