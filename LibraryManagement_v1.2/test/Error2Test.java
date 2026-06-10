import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class Error2Test {

    private final LibraryManager manager = new LibraryManager(new LibraryRepository());

    public static void main(String[] args) throws Exception {
        new Error2Test().cannotReturnBookBorrowedByAnotherUser();
        System.out.println("[PASS] 다른 사용자가 대출한 도서는 반납할 수 없습니다.");
    }

    private void setPrivateField(String fieldName, Object value) throws Exception {
        Field field = LibraryManager.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(manager, value);
    }

    @Test
    @DisplayName("다른 사용자가 대출한 도서는 반납할 수 없어야 한다")
    void cannotReturnBookBorrowedByAnotherUser() throws Exception {
        Map<Integer, Book> testBooks = new HashMap<>();
        testBooks.put(1, new Book(1, "테스트 도서", "테스트 저자", true, "null"));
        setPrivateField("bookMap", testBooks);

        setPrivateField("currentUser", new User("user01", "1111", "USER"));
        boolean borrowResult = manager.borrowBook(1);

        assertTrue(borrowResult);
        assertFalse(manager.getBookMap().get(1).isAvailable());
        assertEquals("user01", manager.getBookMap().get(1).getBorrowerId());

        setPrivateField("currentUser", new User("user02", "2222", "USER"));
        boolean returnResult = manager.returnBook(1);

        assertFalse(returnResult);
        assertFalse(manager.getBookMap().get(1).isAvailable());
        assertEquals("user01", manager.getBookMap().get(1).getBorrowerId());
    }
}
