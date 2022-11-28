import TreePackage.BinarySearchTree.BinarySearchTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;


public class Parser {

    private HashSet<String> reservedWords = new HashSet<>();
    private BinarySearchTree<String> keyWords = new BinarySearchTree<>();
    private BinarySearchTree<String> identifiers = new BinarySearchTree<>();

    /**
     * Adds all words from given file into the reserved words HashSet
     * @param inputLocation location of the file (file path)
     * @throws FileNotFoundException
     */
    public void initializeReservedWords(String inputLocation) throws FileNotFoundException {
        //File object that contains data from the file location
        File file = new File(inputLocation);
        //Scanner object to read an input
        Scanner input = new Scanner(file);

        //reads each line of the file and adds it to the reservedWords ArrayList
        //Runtime O(N)
        while(input.hasNextLine()){
            reservedWords.add(input.nextLine());
        }
        //ends input stream
        input.close();
    }

    //Displays the tree containing keywords using preorder traversal
    public void getReservedWordsBST(){
        keyWords.iterativePreorderTraverse();
    }

    //Displays the tree containing identifiers using preorder traversal
    public void getIdentifiersBST(){
        identifiers.iterativePreorderTraverse();
    }

    /**
     * Reads each token from a given java file
     * if the token is a reserved word, add it to the keyword tree
     * if not, add it to the identifiers tree
     * @param inputLocation location of the program (file path)
     * @throws FileNotFoundException
     */
    public void findIdentifiers(String inputLocation) throws FileNotFoundException {
        //File that will contain data from the given file
        File file = new File(inputLocation);
        //Scanner object to read the data in the file
        Scanner input = new Scanner(file);

        //Reads each word in the program
        //Runtime O(N)
        while(input.hasNext()) {
            //string to hold the current word
            String word = input.next();
            //if it is a reserved word, add to keywords tree
            if(reservedWords.contains(word)) {
                keyWords.add(word);
                //if not add to identifiers tree
            }else{
                identifiers.add(word);
            }
        }
        //end input stream
        input.close();
    }
}
