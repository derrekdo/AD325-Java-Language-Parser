import TreePackage.BinarySearchTree.BinarySearchTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;


public class Parser {

    private ArrayList<String> reservedWords = new ArrayList<>();
    private BinarySearchTree<String> keyWords = new BinarySearchTree<>();
    private BinarySearchTree<String> identifiers = new BinarySearchTree<>();

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
        input.close();

    }

    public void setBalancedBST(){
        for(int i = 0; i < reservedWords.size(); i++){
            keyWords.add(reservedWords.get(i));
        }
    }

    public void getIdentifiers(){

    }

//    private ArrayList<String> read() throws FileNotFoundException {
//        //file location
//        String inputLocation = "." + File.separator + "input-files" + File.separator + "reservedWords.txt";
//        //File object that contains data from the file location
//        File file = new File(inputLocation);
//        //Scanner object to read an input
//        Scanner input = new Scanner(file);
//
//        //stores each line the file into
//        //Runtime O(N)
//        while(input.hasNextLine()){
//            reservedWords.add(input.nextLine());
//
//        }
//        input.close();
//        return words;
//    }
}
