/*
 * Classname: Utility
 * Programmer: Kyle Dryden
 * Version: Java 17
 * Date: 04/05/2023
 * Description: Utility class used for reading file and providing the result as output.
 */
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utility {


    private static final int[] EXPANSION_TABLE = {
            32, 1,  2,  3,  4,  5,
            4,  5,  6,  7,  8,  9,
            8,  9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32,  1
    };
    private static final int[] FINAL_PERMUTATION_TABLE = {
            16,  7, 20, 21, 29, 12, 28, 17,
            1, 15, 23, 26,  5, 18, 31, 10,
            2,  8, 24, 14, 32, 27,  3,  9,
            19, 13, 30,  6, 22, 11,  4, 25
    };

    private static final int[][][] sbox =
    {
        {
            { 0, 0, 4, 6, 12, 11, 13, 7, 14, 0, 0, 5, 15, 5, 0, 15 },
            { 8, 15, 6, 15, 9, 2, 15, 11, 0, 6, 1, 6, 2, 3, 1, 0 },
            { 1, 5, 2, 14, 0, 3, 3, 15, 4, 6, 0, 13, 14, 5, 6, 8 },
            { 10, 4, 5, 15, 5, 9, 15, 15, 11, 4, 5, 0, 0, 7, 10, 6 },
        },
        {
            { 15, 0, 7, 6, 5, 6, 7, 0, 0, 12, 0, 2, 1, 7, 4, 11 },
            { 12, 12, 5, 8, 2, 10, 13, 13, 11, 9, 1, 2, 14, 15, 9, 15 },
            { 4, 12, 15, 4, 10, 14, 2, 4, 12, 10, 8, 10, 0, 9, 13, 0 },
            { 4, 12, 4, 14, 15, 6, 14, 1, 15, 12, 4, 4, 9, 7, 0, 1 },
        },
        {
            { 8, 5, 11, 3, 8, 5, 14, 4, 8, 0, 13, 5, 12, 13, 12, 8 },
            { 15, 13, 9, 6, 15, 2, 10, 4, 6, 7, 14, 14, 14, 15, 3, 7 },
            { 8, 2, 7, 6, 13, 4, 1, 9, 2, 6, 12, 5, 1, 0, 11, 15 },
            { 15, 9, 11, 8, 15, 8, 6, 11, 0, 10, 7, 15, 13, 7, 11, 13 },
        },
        {
            { 5, 3, 15, 0, 13, 2, 8, 14, 2, 12, 9, 5, 13, 8, 11, 14 },
            { 12, 12, 13, 13, 3, 15, 14, 12, 11, 8, 3, 1, 5, 12, 1, 3 },
            { 2, 6, 8, 15, 6, 14, 2, 0, 12, 8, 13, 6, 3, 13, 15, 10 },
            { 15, 0, 11, 5, 13, 7, 8, 15, 6, 13, 9, 6, 5, 15, 14, 5 },
        },
        {
            { 0, 15, 3, 14, 1, 6, 10, 15, 15, 2, 6, 9, 14, 8, 5, 14 },
            { 9, 13, 1, 14, 3, 13, 8, 12, 6, 11, 7, 10, 14, 5, 15, 6 },
            { 1, 2, 8, 3, 10, 5, 7, 0, 2, 12, 6, 4, 15, 8, 7, 13 },
            { 7, 5, 15, 3, 3, 13, 9, 10, 14, 5, 11, 14, 0, 13, 5, 6 },
        },
        {
            { 5, 1, 3, 1, 10, 2, 8, 13, 6, 10, 14, 9, 2, 6, 12, 2 },
            { 6, 3, 0, 10, 8, 1, 12, 14, 0, 15, 9, 6, 13, 6, 8, 14 },
            { 12, 0, 2, 5, 11, 6, 0, 9, 5, 5, 8, 7, 7, 10, 14, 0 },
            { 1, 9, 15, 15, 6, 0, 14, 0, 14, 1, 12, 3, 7, 6, 3, 7 },
        },
        {
            { 12, 13, 0, 14, 13, 0, 6, 13, 2, 2, 8, 5, 0, 12, 13, 13 },
            { 13, 14, 0, 9, 12, 0, 10, 13, 1, 5, 6, 11, 15, 1, 0, 13 },
            { 2, 5, 2, 6, 14, 2, 3, 10, 11, 11, 12, 2, 3, 10, 11, 5 },
            { 3, 5, 10, 12, 14, 2, 9, 11, 6, 1, 1, 13, 9, 10, 6, 14 },
        },
        {
            { 7, 8, 2, 11, 12, 1, 4, 14, 12, 7, 9, 12, 12, 0, 8, 1 },
            { 8, 5, 12, 9, 3, 1, 3, 0, 12, 8, 5, 11, 9, 10, 3, 1 },
            { 5, 7, 7, 8, 10, 15, 5, 2, 10, 13, 7, 15, 10, 2, 14, 7 },
            { 11, 11, 10, 8, 6, 2, 1, 9, 9, 4, 6, 9, 6, 13, 0, 2 }
        }
    };


    //Method used for reading text files. Returns a list of strings for each line.
    public static List<String> readAllLines(String fileName) throws Exception {
        List<String> result = new ArrayList<>();

        Scanner fileImport = new Scanner(new File(fileName));

        while (fileImport.hasNextLine()) {
            String nextLine = fileImport.nextLine();
            result.add(nextLine);
        }

        return result;
    }

    //Method used for converting a list of floats to an array.
    public static float[] listToArray(List<Float> list) {
        int size = list != null ? list.size() : 0;
        float[] floatArray = new float[size];

        for (int i=0; i<size; i++) {
            floatArray[i] = list.get(i);
        }

        return floatArray;
    }

    public static long des0(long plaintext, long key)
    {
        // Perform initial permutation
        long permutedPlaintext = initialPermutation(plaintext);

        // Split permutedPlaintext into left and right halves
        int left = (int) (permutedPlaintext >>> 32);
        int right = (int) permutedPlaintext;

        // Generate 16 round keys
    //    long[] roundKeys = generateRoundKeys(key);

        // Run 16 rounds
        for (int round = 0; round < 16; round++) {
            int temp = right;
   //         right = left ^ fFunction(right, roundKeys[round]);
            left = temp;
        }

        // Combine left and right halves
        long combined = (((long) right) << 32) | (left & 0xFFFFFFFFL);

        // Perform final permutation
      //  long ciphertext = finalPermutation(combined);

      //  return ciphertext;
        return 0;
    }

    public static long des1(long plaintext, long key)
    {
        // Implement DES1 algorithm
        return 0; // Placeholder, replace with the actual implementation
    }

    public static long des2(long plaintext, long key)
    {
        // Implement DES2 algorithm
        return 0; // Placeholder, replace with the actual implementation
    }

    public static long des3(long plaintext, long key)
    {
        // Implement DES3 algorithm
        return 0; // Placeholder, replace with the actual implementation
    }

    private static long initialPermutation(long plaintext) {
        // Define a variable to store the permuted plaintext
        long permutedPlaintext = 0;

        // Apply the initial permutation table (IP) to the plaintext
        for (int i = 0; i < 64; i++) {
            // Use the IP table to determine the new bit position
          //  int newPosition = IP_TABLE[i];

            // Get the bit value from the original plaintext
           // long bit = (plaintext >>> (64 - newPosition)) & 1;

            // Set the bit value in the permuted plaintext
         //   permutedPlaintext |= (bit << (63 - i));
        }

        return permutedPlaintext;
    }

}
