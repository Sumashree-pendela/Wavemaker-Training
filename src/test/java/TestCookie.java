import java.util.UUID;

public class TestCookie {
    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();

        String cookieValue = uuid.toString();
        System.out.println(cookieValue);
    }
}
