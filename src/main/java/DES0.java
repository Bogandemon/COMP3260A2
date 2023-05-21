/*
 * Classname: DES0
 * Programmer: Josh O'Brien
 * Version: Java 17
 * Date: 16/05/2023
 * Description: DES class for the base algorithm with no changes to it
 */
public class DES0 extends DESBase
{
    public DES0(String plainText, String key)
    {
        super(plainText, key);
    }

    public void encrypt()
    {
        //initial permutation
        String output = permute(text, IP);
        //split into left and right components
        String left = output.substring(0, (output.length()/2));
        String right = output.substring((output.length()/2));
        for(int i = 0; i < 16; i++)
        {
            String originalLeft = left;
            left = right;
            //expansion permutation with the expansion table
            String expandedRight = permute( right, E );
            //xor with the round key
            expandedRight = xor( expandedRight, roundKeys.get(i));
            //sbox sub with the sbox table
            expandedRight = sBoxSubstitution( expandedRight );
            //permute with the permutation
            expandedRight = permute( expandedRight, P );
            //xor back with the left side
            right = xor(originalLeft, expandedRight);
            //add to round outputs for avalanche effect later
            roundOutputs.add( left + right );
        }
        //final permutation with the table
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
            expandedRight = permute( expandedRight, P );
            right = xor(originalLeft, expandedRight);
        }
        output = right + left;
        return permute( output, FP ) ;
    }
}
