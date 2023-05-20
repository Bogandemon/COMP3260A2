/*
 * Classname: KenGeneration
 * Programmer: Kyle Dryden
 * Version: Java 17
 * Date: 20/05/2023
 * Description: KeyGeneration class that handles any key operations, such as shifting, separation, and permutations.
 */

public class KeyGeneration {

    private final StringBuilder keyString; //StringBuilder that holds the key obtained from the text file and permuted from permutation choice 2.
    private StringBuilder leftSide; //StringBuilder that holds the left (C) portion of the key during rounds.
    private StringBuilder rightSide; //StringBuilder that holds the right (D) portion of the key during rounds.
    private StringBuilder roundOutput; //StringBuilder that returns the round output to the encryption class.
    private int[][] permutationChoice1; //2D list variable used for the initial permutation used on the key.
    private int[][] permutationChoice2; //2D list variable used for all permutations performed during rounds for the key.

    public KeyGeneration(String defaultKey) {

        keyString = new StringBuilder();
        leftSide = new StringBuilder();
        rightSide = new StringBuilder();

        keyString.append(defaultKey);

        init();
    }

    //Initialisation method that creates the permutation tables and splits the keys into two equal components.
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

    //Public method used to shift both key segments and perform a round permutation.
    public void keyUpdate(int shiftAmount) {
        shiftKey(shiftAmount, false);
        shiftKey(shiftAmount, true);
        roundPermute();
    }

    //Method that separates the 64-bit key into two equal segments of 28 bits (8 bits are used for parity checking).
    private void keySeparation() {
        int keyCharacter;

        //First double for-loop for the left side/first four rows of the permutation table.
        for (int i=0; i<permutationChoice1.length/2; i++) {
            for (int m=0; m<permutationChoice1.length-1; m++) {
                keyCharacter = permutationChoice1[i][m];

                leftSide.append(keyString.substring(keyCharacter-1, keyCharacter));
            }
        }

        //Second double for-loop for the right side/last four rows of the permutation table.
        for (int i=0; i<permutationChoice1.length/2; i++) {
            for (int m=0; m<permutationChoice1.length-1; m++) {
                keyCharacter = permutationChoice1[i+(permutationChoice1.length/2)][m];

                rightSide.append(keyString.substring(keyCharacter-1, keyCharacter));
            }
        }
    }

    //Method for performing a round permutation with table 2.
    private void roundPermute() {
        roundOutput = new StringBuilder();
        StringBuilder tempCombine = leftSide.append(rightSide);
        int keyCharacter;

        for (int[] ints : permutationChoice2) {
            for (int m = 0; m < 8; m++) {
                keyCharacter = ints[m];

                roundOutput.append(tempCombine.charAt(keyCharacter - 1));
            }
        }

        leftSide.delete(28, 56); //Used to remove from the StringBuilder when appending for tempCombine.
    }

    //Method used for shifting the key to the left. shiftAmount will only take one or two as its values.
    private void shiftKey(int shiftAmount, boolean leftCheck) {
        StringBuilder outputString = new StringBuilder();
        StringBuilder loopString;

        if (leftCheck) {
            loopString = leftSide;
        }

        else {
            loopString = rightSide;
        }

        //For loop that evaluates where each value will be placed in the output.
        for (int i = 0; i < loopString.length(); i++) {
            int index = (i + shiftAmount) % 28;

            outputString.append(loopString.substring(index, index + 1));
        }

        if (leftCheck) {
            leftSide = outputString;
        }

        else {
            rightSide = outputString;
        }
    }

    //Public getter.
    public String getRoundOutput() { return roundOutput.toString(); }
}
