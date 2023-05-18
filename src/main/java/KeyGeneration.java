import java.util.List;

public class KeyGeneration {

    private List<String> keyString;

    public KeyGeneration(String fileName) {
        try {
            //keyString = Utility.readAllLines(fileName);
        }

        catch (Exception e) {
            System.err.println("Error Occurred: " + e);
        }
    }

    //TO-DO
    public List<String> leftSide() {
        return keyString;
    }

    //TO-DO
    public List<String> rightSide() {
        return keyString;
    }

    //TO-DO
    private List<String> keySeparation() {
        return keyString;
    }

    //TO-DO
    public List<String> shiftKey() {
        return keyString;
    }


}
