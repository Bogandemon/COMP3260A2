import java.util.Vector;

public class DES0 extends DESBase
{
    public DES0(String plainText, String key) {
        super(plainText, key);
    }

    public void encrypt()
    {
        //56 bit key after permuting against PC1

        String output = permute(plainText, IP);

        String left = output.substring(0, (output.length()/2));
        String right = output.substring((output.length()/2));
        for(int i = 0; i < 16; i++)
        {
            String tempR =  permute(right, E);
            String tempL = right;
            tempR = sBoxSubstitution(tempR);
            tempR =permute(tempR, P);
            right = xor(left, tempR);
            left = tempL;
            roundOutputs.add( left + right );
        }
        output = right + left;
        permute(output, FP);
        roundOutputs.add( permute(output, FP) );
    }
}
