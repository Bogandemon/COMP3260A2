/*
 * Classname: KenGeneration
 * Programmer: Kyle Dryden
 * Version: Java 17
 * Date: 20/05/2023
 * Description: KeyGeneration class that handles any key operations, such as shifting, separation, and permutations.
 */

import java.util.List;
import java.util.ArrayList;

public class KeyGeneration
{
    private final int[][] PC1 =
    {
        {57, 49, 41, 33, 25, 17, 9},
        {1, 58, 50, 42, 34, 26, 18},
        {10, 2, 59, 51, 43, 35, 27},
        {19, 11, 3, 60, 52, 44, 36},
        {63, 55, 47, 39, 31, 23, 15},
        {7, 62, 54, 46, 38, 30, 22},
        {14, 6, 61, 53, 45, 37, 29},
        {21, 13, 5, 28, 20, 12, 4}
    };
    private final int[][] PC2 =
    {
        {14, 17, 11, 24, 1, 5, 3, 28},
        {15, 6, 21, 10, 23, 19, 12, 4},
        {26, 8, 16, 7, 27, 20, 13, 2},
        {41, 52, 31, 37, 47, 55, 30, 40},
        {51, 45, 33, 48, 44, 49, 39, 56},
        {34, 53, 46, 42, 50, 36, 29, 32}
    };

    private final List<String> roundKeys;

    public KeyGeneration(String defaultKey)
    {
        roundKeys = new ArrayList<>();
        StringBuilder keyString = new StringBuilder(defaultKey);
        generateRoundKeys(keyString);
    }

    private void generateRoundKeys(StringBuilder keyString)
    {
        StringBuilder[] keyParts = keySeparation(keyString);
        for (int round = 0; round < 16; round++)
        {
            keyParts = keyUpdate(round, keyParts);
            roundKeys.add(roundPermute(keyParts));
        }
    }

    //Public method used to shift both key segments and perform a round permutation.
    public StringBuilder[] keyUpdate(int round, StringBuilder[] keyParts)
    {
        int shiftAmount = (round == 0 || round == 1 || round == 8 || round == 15) ? 1 : 2;

        StringBuilder leftCopy = new StringBuilder(keyParts[0]);
        StringBuilder rightCopy = new StringBuilder(keyParts[1]);

        shiftKey(shiftAmount, leftCopy);
        shiftKey(shiftAmount, rightCopy);

        return new StringBuilder[]{leftCopy, rightCopy};
    }

    //Method that separates the 64-bit key into two equal segments of 28 bits (8 bits are used for parity checking).
    private StringBuilder[] keySeparation(StringBuilder keyString)
    {
        StringBuilder leftSide = new StringBuilder();
        StringBuilder rightSide = new StringBuilder();
        int keyCharacter;
        //First double for-loop for the left side/first four rows of the permutation table.
        for (int i = 0; i < PC1.length/2; i++)
        {
            for (int m = 0; m < PC1.length-1; m++)
            {
                keyCharacter = PC1[i][m];
                leftSide.append(keyString.substring(keyCharacter-1, keyCharacter));
            }
        }
        //Second double for-loop for the right side/last four rows of the permutation table.
        for (int i = 0; i< PC1.length/2; i++)
        {
            for (int m = 0; m< PC1.length-1; m++)
            {
                keyCharacter = PC1[i+(PC1.length/2)][m];
                rightSide.append(keyString.substring(keyCharacter-1, keyCharacter));
            }
        }
        return new StringBuilder[]{leftSide, rightSide};
    }

    //Method for performing a round permutation with table 2.
    private String roundPermute(StringBuilder[] keyParts)
    {
        StringBuilder roundOutput = new StringBuilder();
        StringBuilder tempCombine = new StringBuilder(keyParts[0]).append(keyParts[1]);
        int keyCharacter;
        for (int[] ints : PC2)
        {
            for (int m = 0; m < 8; m++)
            {
                keyCharacter = ints[m];
                roundOutput.append(tempCombine.charAt(keyCharacter - 1));
            }
        }
        return roundOutput.toString();
    }

    //Method used for shifting the key to the left. shiftAmount will only take one or two as its values.
    private void shiftKey(int shiftAmount, StringBuilder keyPart)
    {
        StringBuilder outputString = new StringBuilder();
        //For loop that evaluates where each value will be placed in the output.
        for (int i = 0; i < keyPart.length(); i++)
        {
            int index = (i + shiftAmount) % 28;
            outputString.append(keyPart.substring(index, index + 1));
        }
        keyPart.replace(0, keyPart.length(), outputString.toString());
    }

    public List<String> getRoundKeys()
    {
        return roundKeys;
    }
}
