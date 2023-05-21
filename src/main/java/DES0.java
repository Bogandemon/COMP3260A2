
public class DES0 extends DESBase
{
    public DES0(String plainText, String key) {
        super(plainText, key);
    }

    public void encrypt()
    {
        String output = permute(plainText, IP);
        String left = output.substring(0, (output.length()/2));
        String right = output.substring((output.length()/2));
        for(int i = 0; i < 16; i++)
        {
            String originalLeft = left;
            left = right;
            String expandedRight = permute( right, E );
            key.keyUpdate(i);
            expandedRight = xor( expandedRight, key.getRoundOutput());
            expandedRight = sBoxSubstitution( expandedRight );
            expandedRight = permute( expandedRight, P );
            right = xor(originalLeft, expandedRight);
            roundOutputs.add( left + right );
        }
        output = right + left;
        roundOutputs.add( permute( output, FP ) );
    }
}
