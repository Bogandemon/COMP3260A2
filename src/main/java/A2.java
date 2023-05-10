import java.util.ArrayList;
import java.util.List;

public class A2 {
    public static void main(String[] args) {

        List<String> plaintext = new ArrayList<>();
        KeyGeneration key = new KeyGeneration("src/resources/Key.txt");

        System.out.println(key.getLeftSide());
        System.out.println(key.getRightSide());

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