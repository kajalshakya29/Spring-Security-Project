import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // --- 1. ADMIN Password ---
        String adminPassword = "admin123"; // Jaise: admin123
        String adminHash = encoder.encode(adminPassword);
        System.out.println("Username: admin, Plaintext: " + adminPassword);
        System.out.println("Admin Hash (60 chars): " + adminHash);

        // --- 2. KAJAL Password ---
        String kajalPassword = "kajal123"; // Jaise: kajal123
        String kajalHash = encoder.encode(kajalPassword);
        System.out.println("\nUsername: kajal, Plaintext: " + kajalPassword);
        System.out.println("Kajal Hash (60 chars): " + kajalHash);

        // --- 3. VISHAKHA Password ---
        String vishakhaPassword = "vish123"; // Jaise: vishakha123
        String vishakhaHash = encoder.encode(vishakhaPassword);
        System.out.println("\nUsername: vishakha, Plaintext: " + vishakhaPassword);
        System.out.println("Vishakha Hash (60 chars): " + vishakhaHash);
    }
}