import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class Error3Test {

    public static void main(String[] args) throws Exception {
        new Error3Test().cannotInputStringInsteadOfNumber();
        System.out.println("[PASS] 숫자가 아닌 입력 처리 테스트 성공");
    }

    @Test
    @DisplayName("숫자 대신 문자열 입력 시 예외 없이 null 반환")
    void cannotInputStringInsteadOfNumber() throws Exception {

        String input = "abc\n";

        Field scannerField = LibraryMain.class.getDeclaredField("sc");
        scannerField.setAccessible(true);
        scannerField.set(null, new Scanner(new ByteArrayInputStream(input.getBytes())));

        Method method =
                LibraryMain.class.getDeclaredMethod("readInt", String.class);

        method.setAccessible(true);

        Integer result =
                (Integer) method.invoke(null, "입력: ");

        assertNull(result);
    }
}