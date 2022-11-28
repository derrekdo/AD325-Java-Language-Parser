import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //File path for the program and list of reserved words
        String fileLocation = "." + File.separator + "src" + File.separator + File.separator + "reservedWords.txt";
        String programLocation = "." + File.separator + "src" + File.separator + File.separator + "Palindrome.java";
        Parser parser = new Parser();

        parser.initializeReservedWords(fileLocation);
        parser.findIdentifiers(programLocation);


        System.out.println("\t\tParser Test");
        System.out.println("-----------------------------");

        System.out.println("List of identifiers found: ");
        parser.getIdentifiersBST();

        System.out.println();
        System.out.println();

        System.out.println("List of Keywords found:");
        parser.getReservedWordsBST();
        System.out.println();
    }
}
