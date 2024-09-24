import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        // creates a charList to change the filetype to and sets up the start and end lists 
        int[] start = new int[2];
        int[] end = new int[2];
        char[][] mazeList = null;

        // reads the file if nothing is thrown which it gets caught if it does 
        try{File mazeFile = new File("maze.dat");
        Scanner scan = new Scanner(mazeFile);

        // gets the rows and columns of the maze and inputs it into a charList
        String line = scan.nextLine();
        int row = Integer.parseInt(line.split("\\s+")[0]);
        int col = Integer.parseInt(line.split("\\s+")[1]);
        mazeList = new char[row][col];
        
        // populates the charList with all the values and finds out position to start and end position while doing so
        for(int i = 0; i < row; i++) {
            String data = scan.nextLine().trim();
            for(int j = 0; j < data.length(); j++){
                mazeList[i][j] = data.charAt(j);
                if(data.charAt(j) == '+'){
                    start[0] = i;
                    start[1] = j;
                }
                if(data.charAt(j) == '-'){
                    end[0] = i;
                    end[1] = j;
                }
            }
            if(scan.hasNextLine() != true){
                break;
            }
        }
        scan.close();
        
    }catch(FileNotFoundException e){
            System.out.println("An error occured");
            e.printStackTrace();
    } 
        solve(mazeList, start[1], start[0]);
        pMaze(mazeList);
        

        
    }
    public static void pMaze(char[][] maze){
        for (char[] line : maze){
            for(char c : line){
                System.out.print(c);
            }
            System.out.println();
        }
    }
    //used for testing if start and end were at the right coordinates and made sure th
    public void pCoordinates(int[] m){
        for(int i : m){
            System.out.print(i + ", ");
        }
        
    }
    public static boolean canMove(char[][] maze, int col, int row){
       if(row < 0 || row > maze.length || col < 0 || col > maze[0].length){
            return false;
       }
       return true;
    }

    public static boolean solve(char[][] maze, int column, int row){
        if(canMove(maze, column, row)){
            if(maze[row][column] == 'X'){
                return false;
            }
            if(maze[row][column] == '-'){
                return true;
            }
            maze[row][column] = '+';

            // right
            if(solve(maze, column + 1, row)){
                return true;
            }

            // down
            if(solve(maze, column, row + 1)){
                return true;
            }

            // left
            if(solve(maze, column- 1, row)){
                return true;
            }
            
            // up
            if(solve(maze, column, row - 1)){
                return true;
            }
            // backtrack
            maze[row][column] = '.';
            return false;
        }
        return false;
    }
}
    

