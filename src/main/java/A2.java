import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.crypto.KeyGenerator;

public class A2 {
    public static void main(String[] args)
    {
                int[][][] sBoxes = new int[8][4][16];
                Random random = new Random();

                // Fill the S-boxes with random values
                for (int s = 0; s < 8; s++) {
                    System.out.print( "{ ");
                    for (int row = 0; row < 4; row++) {
                        System.out.print( "{ ");
                        for (int col = 0; col < 16; col++)
                        {
                                int randoem = random.nextInt(16);
                            while( notThere( sBoxes[s][row], randoem )   )
                            {
                                sBoxes[s][row][col] = random.nextInt(16);
                                System.out.print( sBoxes[s][row][col] + ", ");
                                randoem =  random.nextInt(16);
                            }
                        }
                        System.out.println( "}, ");
                    }
                    System.out.println( "}, ");
                }

        System.out.println(sBoxes);
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

    private static boolean notThere(int[] sBox, int provided)
    {

        boolean amount=false;
        for(int s: sBox)
        {
            if(s == provided )
            {
               amount = true;
            }
        }
        if(  sBox.length>15)
        {
            amount=false;
        }
        return amount;
    }
}