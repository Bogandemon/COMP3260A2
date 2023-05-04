import java.util.ArrayList;
import java.util.List;

public class A2 {
    public static void main(String[] args) {

        List<String> plaintext = new ArrayList<>();

        try {
            plaintext = Utility.readAllLines("src/resources/Plaintext.txt");
        }

        catch (Exception e) {
            System.err.println("Error Occurred: " + e);
        }

        for (int i=0; i<plaintext.size(); i++) {
            System.out.println(plaintext.get(i));
        }
    }
}