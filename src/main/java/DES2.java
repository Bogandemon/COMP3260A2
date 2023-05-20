public class DES2 extends  DESBase
{
    public DES2(String plainText, String key)
    {
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

            if  (i == 0 || i == 1 || i == 8 || i == 15) {
                key.keyUpdate(1);
            }

            else {
                key.keyUpdate(2);
            }

            expandedRight = xor( expandedRight, key.getRoundOutput() );
            expandedRight = permute( expandedRight, IEP );
            expandedRight = permute( expandedRight, P );
            right = xor(originalLeft, expandedRight);
            roundOutputs.add( left + right );
        }
        output = right + left;
        roundOutputs.add( permute( output, FP ) );
    }
}
