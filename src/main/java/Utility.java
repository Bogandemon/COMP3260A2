/*
 * Classname: Utility
 * Programmer: Kyle Dryden
 * Version: Java 17
 * Date: 04/05/2023
 * Description: Utility class used for reading file and providing the result as output.
 */
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utility {

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
}
