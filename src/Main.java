import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String inputLocation = "." + File.separator + "input-files" + File.separator + "reservedWords.txt";
        Parser parser = new Parser();
        parser.initializeReservedWords(inputLocation);
        parser.setBalancedBST();

        Palindrome.main(null);
    }

}
