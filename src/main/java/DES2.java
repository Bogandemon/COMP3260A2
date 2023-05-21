/*
 * Classname: DES2
 * Programmer: Josh O'Brien
 * Version: Java 17
 * Date: 16/05/2023
 * Description: DES2 handles the third iteration of the DES simulation. This version is missing S-box functionality.
 */

public class DES2 extends  DESBase
{
    public DES2(String plainText, String key)
    {
        super(plainText, key);
    }

    public void encrypt()
    {
        //same process as des0 except s-box permutation is replaced with the inverse expansion table
        String output = permute(text, IP);
        String left = output.substring(0, (output.length()/2));
        String right = output.substring((output.length()/2));
        for(int i = 0; i < 16; i++)
        {
            String originalLeft = left;
            left = right;
            String expandedRight = permute( right, E );
            expandedRight = xor( expandedRight, roundKeys.get(i) );
            expandedRight = permute( expandedRight, IEP );
            expandedRight = permute( expandedRight, P );
            right = xor(originalLeft, expandedRight);
            roundOutputs.add( left + right );
        }
        output = right + left;
        roundOutputs.add( permute( output, FP ) );
    }

    public String decrypt()
    {
        //same process as encryption but with round keys reversed
        String output = permute(text, IP);
        String left = output.substring(0, output.length()/2);
        String right = output.substring(output.length()/2);
        for (int i=0; i<16; i++)
        {
            String originalLeft = left;
            left = right;
            String expandedRight = permute( right, E );
            expandedRight = xor( expandedRight, roundKeys.get(15-i));
            expandedRight = permute( expandedRight, IEP );
            expandedRight = permute( expandedRight, P );
            right = xor(originalLeft, expandedRight);
        }
        output = right + left;
        return permute( output, FP ) ;
    }
}
