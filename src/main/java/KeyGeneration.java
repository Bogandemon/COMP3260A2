/*
 * Classname: KenGeneration
 * Programmer: Kyle Dryden
 * Version: Java 17
 * Date: 10/05/2023
 * Description: KeyGeneration class that handles any key operations, such as shifting, separation, and permutations.
 */

import java.util.List;

public class KeyGeneration {

    private String keyString; //String variable that holds the key obtained from the text file and permutation from permutated choice 2.
    private String leftSide; //String variable that holds the left (C) portion of the key during rounds.
    private String rightSide; //String variable that holds the right (D) portion of the key during rounds.
    private int[][] permutationChoice1; //2D list variable used for the initial permutation used on the key.
    private int[][] permutationChoice2; //2D list variable used for all permutations performed during rounds for the key.

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

    public void roundPermute() {
        String outputString = "";
        String tempCombine = leftSide + rightSide;
        int keyCharacter = 0;

        keyString = "";

        for (int i=0; i< permutationChoice2.length; i++) {
            for (int m = 0; m<8; m++) {
                keyCharacter = permutationChoice2[i][m];

                keyString += tempCombine.substring(keyCharacter - 1, keyCharacter);
            }
        }
    }

    public void shiftKey(int shiftAmount, boolean leftCheck) {
        String outputString = "";
        String loopString = "";

        if (leftCheck) {
            loopString = leftSide;
        }

        else {
            loopString = rightSide;
        }

        for (int i = 0; i < loopString.length(); i++) {
            int index = (i + shiftAmount) % 28;

            outputString += loopString.substring(index, index + 1);
        }

        if (leftCheck) {
            leftSide = outputString;
        }

        else {
            rightSide = outputString;
        }
    }

    public String getKeyString() {
        return keyString;
    }

    public String getLeftSide() {
        return leftSide;
    }

    public String getRightSide() {
        return rightSide;
    }
}
