import java.util.List;
import java.util.Random;

public class KeyGeneration {

    private String keyString;
    private String leftSide;
    private String rightSide;

    public KeyGeneration(String fileName) {
        keyString = "";
        leftSide = "";
        rightSide = "";

        try {
            List<String> convertString = Utility.readAllLines(fileName);

            for (String s : convertString) {
                keyString += s;
            }
        }

        catch (Exception e) {
            System.err.println("Error Occurred: " + e);
        }
    }

    public String getKey() {
        return keyString;
    }

    public String getLeftSide() {
        keySeparation();

        return leftSide;
    }

    public String getRightSide() {
        return leftSide;
    }

    private void keySeparation() {
        for (int i=0; i<keyString.length(); i++) {
            if (i < keyString.length()/2) {
                leftSide = keyString.substring(i, i+1);
                continue;
            }

            rightSide = keyString.substring(i, i+1);
        }
    }

    //TO-DO
    public void shiftKey() {
        int shiftAmount;
        String outputString = "";
        Random random = new Random();

        shiftAmount = random.nextInt(1,3);

        for (int i=0; i<keyString.length(); i++) {
            if (i-shiftAmount < 0) {
                outputString += keyString.substring(keyString.length()+(i-shiftAmount), keyString.length()+((i+1)-shiftAmount));
                continue;
            }

            outputString += keyString.substring(i-shiftAmount, (i+1)-shiftAmount);
        }

        keyString = outputString;
    }


}
