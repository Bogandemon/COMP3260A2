import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.crypto.KeyGenerator;

public class A2
{
    public static void main(String[] args)
    {
        List<String> plaintext = new ArrayList<>();
        List<List<List<Integer>>> sBoxes = generateSBoxes();
        try
        {
            plaintext = Utility.readAllLines("src/resources/Plaintext.txt");
        }

        catch (Exception e)
        {
            System.err.println("Error Occurred: " + e);
        }

        for (int i=0; i<plaintext.size(); i++)
        {
            System.out.println(plaintext.get(i));
        }
    }

    private static List<List<List<Integer>>> generateSBoxes()
    {
        List<List<List<Integer>>> sBoxes = new ArrayList<>();
        Random random = new Random();
        for(int s = 0; s < 8; s++)
        {
            List<List<Integer>> sbox = new ArrayList<>();
            for( int row = 0; row < 4; row++ )
            {
                List<Integer> rowInt = new ArrayList<>();
                while( rowInt.size()<16 )
                {
                    int randomInt = random.nextInt( 16 );
                    if( !rowInt.contains( randomInt ) )
                    {
                        rowInt.add( randomInt );
                    }
                }
                sbox.add( rowInt );
            }
            sBoxes.add( sbox );
        }
        return sBoxes;
    }
}