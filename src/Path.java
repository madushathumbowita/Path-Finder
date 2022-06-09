/**
  w1790818, 20191207
  Madusha Thumbowita
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Path {
    private static final ArrayList<String> eachRow = new ArrayList<>(); // Arraylist to store each row of a puzzle

    public static void main (String [] args){ // Reading the puzzle file
        int count = 0;
        try {

            File file = new File("src/Puzzle");
            Scanner obj = new Scanner(file);
            while (obj.hasNextLine()) {
                String data = obj.nextLine();
                eachRow.add(data);
                count++;
            }
            obj.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        char[][] puzzle = new char[count][count];

        for(int i = 0; i< puzzle.length; i++){
            puzzle[i] = eachRow.get(i).toCharArray();
        }
        System.out.println(" Puzzle  : ");
        System.out.println();
        for(int j = 0; j< puzzle.length; j++){
            for(int k = 0; k< puzzle[j].length; k++){
                System.out.print(" "+ puzzle[j][k]);
            }
            System.out.println();
        }


        //Calculate and output the total time
        Chosen_Algo chosenAlgoPath = new Chosen_Algo(puzzle); //Object of class Chosen_Algo
        long start = System.currentTimeMillis();

        chosenAlgoPath.find();
        long end = System.currentTimeMillis();
        System.out.println("Time Elapsed: " + (end-start)+" miliseconds");


    }
}
