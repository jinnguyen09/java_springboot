package Exercise.demo.Service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class DictionaryService {

    private static final Map<String, String> dictionary = new HashMap<>();

    static {
        dictionary.put("hello", "Xin chào");
        dictionary.put("book", "Quyển sách");
        dictionary.put("apple", "Quả táo");
        dictionary.put("computer", "Máy tính");
        dictionary.put("developer", "Lập trình viên");
    }

    /**
     * Hàm tìm kiếm nghĩa của từ
     * @param word Từ tiếng Anh
     * @return Nghĩa tiếng Việt, hoặc null nếu không tìm thấy
     */
    public String findMeaning(String word) {
        if (word == null) {
            return null;
        }
        return dictionary.get(word.toLowerCase());
    }
}