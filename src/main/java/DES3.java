/*
 * Classname: DES3
 * Programmer: Josh O'Brien
 * Version: Java 17
 * Date: 16/05/2023
 * Description: DES3 handles the fourth iteration of the DES simulation. This version is missing permutation P from all rounds.
 */

public class DES3 extends DESBase
{
    public DES3(String plainText, String key)
    {
        super(plainText, key);
    }

    public void encrypt()
    {
        //same process as des0 except straight permutation is removed
        String output = permute(text, IP);
        String left = output.substring(0, (output.length()/2));
        String right = output.substring((output.length()/2));
        for(int i = 0; i < 16; i++)
        {
            String originalLeft = left;
            left = right;
            String expandedRight = permute( right, E );
            expandedRight = xor( expandedRight, roundKeys.get(i) );
            expandedRight = sBoxSubstitution( expandedRight );
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
            expandedRight = sBoxSubstitution( expandedRight );
            right = xor(originalLeft, expandedRight);
        }
        output = right + left;
        return permute( output, FP ) ;
    }
}
