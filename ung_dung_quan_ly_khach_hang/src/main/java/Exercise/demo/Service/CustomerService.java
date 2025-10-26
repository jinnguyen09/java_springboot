package Exercise.demo.Service;

import Exercise.demo.Model.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CustomerService {

    private final Map<Long, Customer> customerMap = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public CustomerService() {
        save(new Customer(null, "Nguyen Dinh Thiep", "thiepthanhx03@gmail.com", "Nghe An"));
        save(new Customer(null, "Nguyen Dinh Thang", "thangthanhx37@gmail.com", "Thanh Hoa"));
        save(new Customer(null, "Nguyen Dinh Dat", "DinhDat95@gmail.com", "Ha Noi"));
        save(new Customer(null, "Hoang Thi Huong", "HuongHoang@codegym.vn", "Sai Gon"));
        save(new Customer(null, "Nguyen Dinh Thanh", "DinhThanh@gmail.com", "Viet Nam"));
    }

    public List<Customer> findAll() {
        return new ArrayList<>(customerMap.values());
    }

    public Customer findById(Long id) {
        return customerMap.get(id);
    }

    public void save(Customer customer) {
        if (customer.getId() == null || customer.getId() == 0) {
            long newId = counter.incrementAndGet();
            customer.setId(newId);
            customerMap.put(newId, customer);
        } else {
            customerMap.put(customer.getId(), customer);
        }
    }
}