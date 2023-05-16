import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.crypto.KeyGenerator;

public class A2
{
    public static void main(String[] args)
    {
        List<String> input = new ArrayList<>();
        try
        {
            input = Utility.readAllLines("src/resources/Plaintext.txt");
        }

        catch (Exception e)
        {
            System.err.println("Error Occurred: " + e);
        }

        List<List<Integer>> avalancheResults = new ArrayList<>();
        DES0 desOP = new DES0(input.get(0), input.get(2));
        DES0 des0Q = new DES0(input.get(1), input.get(2));
        desOP.encrypt();
        des0Q.encrypt();
        avalancheResults.add(avalanche(desOP.roundOutputs, des0Q.roundOutputs));

      for(int i=0; i<16; i++)
      {
          System.out.print(i+" ");
          for(int j=0; j<1; j++)
          {
              System.out.print(avalancheResults.get(j).get(i) +" ");
          }
          System.out.print("\n");
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
}