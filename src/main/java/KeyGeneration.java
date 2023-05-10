import java.util.List;
import java.util.Random;

public class KeyGeneration {

    private String keyString;
    private String leftSide;
    private String rightSide;
    private int[][] permutationChoice1;
    private int[][] permutationChoice2;

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

        init();
    }

    private void init() {
        permutationChoice1 = new int[][]{{57, 49, 41, 33, 25, 17, 9},
                                        {1, 58, 50, 42, 34, 26, 18},
                                        {10, 2, 59, 51, 43, 35, 27},
                                        {19, 11, 3, 60, 52, 44, 36},
                                        {63, 55, 47, 39, 31, 23, 15},
                                        {7, 62, 54, 46, 38, 30, 22},
                                        {14, 6, 61, 53, 45, 37, 29},
                                        {21, 13, 5, 28, 20, 12, 4}};

        permutationChoice2 = new int[][]{{14, 17, 11, 24, 1, 5, 3, 28},
                                        {15, 6, 21, 10, 23, 19, 12, 4},
                                        {26, 8, 16, 7, 27, 20, 13, 2},
                                        {41, 52, 31, 37, 47, 55, 30, 40},
                                        {51, 45, 33, 48, 44, 49, 39, 56},
                                        {34, 53, 46, 42, 50, 36, 29, 32}};

        keySeparation();
    }

    private void keySeparation() {
        int keyCharacter = 0;
        for (int i=0; i<permutationChoice1.length/2; i++) {
            for (int m=0; m<permutationChoice1.length-1; m++) {
                keyCharacter = permutationChoice1[i][m];

                leftSide += keyString.substring(keyCharacter-1, keyCharacter);
            }
        }

        for (int i=0; i<permutationChoice1.length/2; i++) {
            for (int m=0; m<permutationChoice1.length-1; m++) {
                keyCharacter = permutationChoice1[i+(permutationChoice1.length/2)][m];

                rightSide += keyString.substring(keyCharacter-1, keyCharacter);
            }
        }
    }

    public void endPermute() {

    }

    public String getKey() {
        return keyString;
    }

    public String getLeftSide() {
        return leftSide;
    }

    public String getRightSide() {
        return rightSide;
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
