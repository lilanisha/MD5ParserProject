import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import org.json.JSONObject;

public class MD5Parser {
    public static void main(String[] args) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("input.json")));
            JSONObject jsonObject = new JSONObject(content);

            if (!jsonObject.has("student")) {
                System.out.println("Error: 'student' object not found in JSON.");
                return;
            }

            JSONObject student = jsonObject.getJSONObject("student");

            String firstName = student.optString("first_name", "").toLowerCase().replace(" ", "");
            String rollNumber = student.optString("roll_number", "").toLowerCase().replace(" ", "");

            if (firstName.isEmpty() || rollNumber.isEmpty()) {
                System.out.println("Error: 'first_name' or 'roll_number' is missing or empty.");
                return;
            }

            String hash = generateMD5(firstName + rollNumber);

            Files.write(Paths.get("output.txt"), hash.getBytes());

            System.out.println("MD5 hash stored in output.txt: " + hash);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static String generateMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return "Error generating MD5";
        }
    }
}
