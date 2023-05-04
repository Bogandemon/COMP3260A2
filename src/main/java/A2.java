import java.util.ArrayList;
import java.util.List;

public class A2 {
    public static void main(String[] args) {

        List<String> plaintext = new ArrayList<>();
        KeyGeneration key = new KeyGeneration("src/resources/Key.txt");

        key.shiftKey();
        key.shiftKey();
        key.shiftKey();

        System.out.println(key.getKey());

        try {
            plaintext = Utility.readAllLines("src/resources/Plaintext.txt");
        }

        catch (Exception e) {
            System.err.println("Error Occurred: " + e);
        }

        for (String s : plaintext) {
            System.out.println(s);
        }
    }
}