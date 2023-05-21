import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class A2
{
    public static void main(String[] args)
    {

        //Variables that hold the input given from the text files.
        List<String> input = new ArrayList<>();
        List<String> decryptInput = new ArrayList<>();

        try
        {
            input = Files.readAllLines(Path.of("encryptionInput.txt"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        List<List<Integer>> avalancheResults = new ArrayList<>(); //Variable that keeps track of all integer values used for the report output.
        long startTime = System.nanoTime(); //Variable that tracks how long the system takes to encrypt.

        //Bank of DES variables used for the first set(two Plaintexts with one Key).
        DES0 des0P = new DES0(input.get(0), input.get(2));
        DES0 des0Q = new DES0(input.get(1), input.get(2));
        des0P.encrypt();
        des0Q.encrypt();
        avalancheResults.add(avalanche(des0P.roundOutputs, des0Q.roundOutputs));

        DES1 des1P = new DES1(input.get(0), input.get(2));
        DES1 des1Q = new DES1(input.get(1), input.get(2));
        des1P.encrypt();
        des1Q.encrypt();
        avalancheResults.add(avalanche(des1P.roundOutputs, des1Q.roundOutputs));

        DES2 des2P = new DES2(input.get(0), input.get(2));
        DES2 des2Q = new DES2(input.get(1), input.get(2));
        des2P.encrypt();
        des2Q.encrypt();
        avalancheResults.add(avalanche(des2P.roundOutputs, des2Q.roundOutputs));

        DES3 des3P = new DES3(input.get(0), input.get(2));
        DES3 des3Q = new DES3(input.get(1), input.get(2));
        des3P.encrypt();
        des3Q.encrypt();
        avalancheResults.add(avalanche(des3P.roundOutputs, des3Q.roundOutputs));

        //Second bank of DES variables used for output (one Plaintext, two Keys).
        List<List<Integer>> avalancheResults2 = new ArrayList<>();
        DES0 des0K = new DES0(input.get(0), input.get(2));
        DES0 des0L = new DES0(input.get(0), input.get(3));
        des0K.encrypt();
        des0L.encrypt();
        avalancheResults2.add(avalanche(des0K.roundOutputs, des0L.roundOutputs));

        DES1 des1K = new DES1(input.get(0), input.get(2));
        DES1 des1L = new DES1(input.get(0), input.get(3));
        des1K.encrypt();
        des1L.encrypt();
        avalancheResults2.add(avalanche(des1K.roundOutputs, des1L.roundOutputs));

        DES2 des2K = new DES2(input.get(0), input.get(2));
        DES2 des2L = new DES2(input.get(0), input.get(3));
        des2K.encrypt();
        des2L.encrypt();
        avalancheResults2.add(avalanche(des2K.roundOutputs, des2L.roundOutputs));

        DES3 des3K = new DES3(input.get(0), input.get(2));
        DES3 des3L = new DES3(input.get(0), input.get(3));
        des3K.encrypt();
        des3L.encrypt();
        avalancheResults2.add(avalanche(des3K.roundOutputs, des3L.roundOutputs));

        //Used to calculate how long the calculations took
        long endTime = System.nanoTime();
        double time = (double) (endTime - startTime) /1000000000;

        //Try statement for creating the output for the avalancheReport file
        try( PrintWriter writer = new PrintWriter("avalancheReport.txt") )
        {
            writer.println( "Avalanche Demonstration" );
            writer.println( "Plaintext P: " + input.get(0) );
            writer.println( "Plaintext P’: " + input.get(1) );
            writer.println( "Key K: " + input.get(2) );
            writer.println( "Key K’: " + input.get(2) );
            writer.println( "Total running time: " + time + " seconds" );

            //Output statements used for the first set of DES tests.
            writer.println("\nP and P’ under K");
            writer.println( "Ciphertext C DES0: " + des0P.roundOutputs.get(16) );
            writer.println( "Ciphertext C’ DES0: " + des0Q.roundOutputs.get(16) );
            writer.println( "\nCiphertext C DES1: " + des1P.roundOutputs.get(16) );
            writer.println( "Ciphertext C’ DES1: " + des1Q.roundOutputs.get(16) );
            writer.println( "\nCiphertext C DES2: " + des2P.roundOutputs.get(16) );
            writer.println( "Ciphertext C’ DES2: " + des2Q.roundOutputs.get(16) );
            writer.println( "\nCiphertext C DES3: " + des3P.roundOutputs.get(16) );
            writer.println( "Ciphertext C’ DES3: " + des3Q.roundOutputs.get(16) );
            writer.println("\nRound    " + "DES0    " + "DES1    " + "DES2    " + "DES3    ");
            outPutAvalanche( avalancheResults, writer );

            //Output statements used for the second set of DES tests.
            writer.println("\nP under K and K’");
            writer.println( "Ciphertext C DES0: " + des0K.roundOutputs.get(16) );
            writer.println( "Ciphertext C’ DES0: " + des0L.roundOutputs.get(16) );
            writer.println( "\nCiphertext C DES1: " + des1K.roundOutputs.get(16) );
            writer.println( "Ciphertext C’ DES1: " + des1L.roundOutputs.get(16) );
            writer.println( "\nCiphertext C DES2: " + des2K.roundOutputs.get(16) );
            writer.println( "Ciphertext C’ DES2: " + des2L.roundOutputs.get(16) );
            writer.println( "\nCiphertext C DES3: " + des3K.roundOutputs.get(16) );
            writer.println( "Ciphertext C’ DES3: " + des3L.roundOutputs.get(16) );
            writer.println("\nRound    " + "DES0    " + "DES1    " + "DES2    " + "DES3    ");
            outPutAvalanche( avalancheResults2, writer );
        }
        catch ( Exception e )
        {
            throw new RuntimeException(e);
        }

        //Decryption related material
        try {
            decryptInput = Files.readAllLines(Path.of("decryptionInput.txt"));
        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Bank of new DES variables used for decryption
        DES0 des0C = new DES0(decryptInput.get(0),decryptInput.get(1));
        DES1 des1C = new DES1(decryptInput.get(0),decryptInput.get(1));
        DES2 des2C = new DES2(decryptInput.get(0),decryptInput.get(1));
        DES3 des3C = new DES3(decryptInput.get(0),decryptInput.get(1));

        //Used to create the output to send to the output text file
        try ( PrintWriter writer = new PrintWriter("decryption.txt") )
        {
            writer.println("DECRYPTION");
            writer.println("Ciphertext C:     " + decryptInput.get(0));
            writer.println("Key K:            " + decryptInput.get(1));
            writer.println();
            writer.println("Plaintext P DESO: " +  des0C.decrypt() );
            writer.println("Plaintext P DES1: " +  des1C.decrypt() );
            writer.println("Plaintext P DES2: " +  des2C.decrypt() );
            writer.println("Plaintext P DES3: " +  des3C.decrypt() );
        }
        catch ( Exception e )
        {
            throw new RuntimeException(e);
        }
    }


    private static List<Integer> avalanche(List<String> roundOutputs, List<String> roundOutputs1)
    {
        List<Integer> results = new ArrayList<>();
        for( int i=0; i<roundOutputs.size(); i++)
        {
            int count=0;
            for(int j=0; j<64; j++)
            {
                if( roundOutputs.get(i).charAt(j) != roundOutputs1.get(i).charAt(j))
                {
                    count++;
                }
            }
            results.add(count);
        }
        return results;
    }

    private static void outPutAvalanche( List<List<Integer>> avalancheResults, PrintWriter writer )
    {
        for(int i=0; i<17; i++)
        {
            writer.printf("%-9d", i);
            for (int j = 0; j < 4; j++)
            {
                String result = String.valueOf(avalancheResults.get(j).get(i));
                writer.printf("%-8s", result);
            }
            writer.println();
        }
    }
}