/*
 * Classname: DES1
 * Programmer: Josh O'Brien
 * Version: Java 17
 * Date: 16/05/2023
 * Description: DES1 handles the second iteration of the DES simulation. This version is missing the round key XOR component from all functions.
 */

public class DES1 extends DESBase
{
    public DES1(String plainText, String key)
    {
        super(plainText, key);
    }

    public void encrypt()
    {
        //same process as des0 except round key xor is removed
        String output = permute(text, IP);
        String left = output.substring(0, (output.length()/2));
        String right = output.substring((output.length()/2));
        for(int i = 0; i < 16; i++)
        {
            String originalLeft = left;
            left = right;
            String expandedRight = permute( right, E );
            expandedRight = sBoxSubstitution( expandedRight );
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
            expandedRight = sBoxSubstitution( expandedRight );
            expandedRight = permute( expandedRight, P );
            right = xor(originalLeft, expandedRight);
        }
        output = right + left;
        return permute( output, FP ) ;
    }
}
